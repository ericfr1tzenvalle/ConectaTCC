package com.ifrs.conectatcc.service;

import com.ifrs.conectatcc.dto.CandidaturaDTO;
import com.ifrs.conectatcc.model.*;
import com.ifrs.conectatcc.repository.CandidaturaRepository;
import com.ifrs.conectatcc.repository.PropostaRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CandidaturaService {
    private final CandidaturaRepository candidaturaRepository;
    private final PropostaRepository propostaRepository;

    public CandidaturaService(CandidaturaRepository candidaturaRepository, PropostaRepository propostaRepository) {
        this.candidaturaRepository = candidaturaRepository;
        this.propostaRepository = propostaRepository;
    }
    //TODO: criar tratamentoDeErrosPersonalizados aqui com HTTPErrors 
    @Transactional
    public CandidaturaDTO criarCandidatura(Long propostaId, String mensagem){
        Aluno alunoLogado = (Aluno) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PropostaTCC proposta = propostaRepository.findById(propostaId).orElseThrow(() -> new RuntimeException("Proposta não encontrada"));
        if(proposta.getStatus() != StatusTCC.DISPONIVEL){
            throw new RuntimeException("Só é possível candidatar-se a propostas com status 'Disponível'.");
        }
        boolean jaCandidato = proposta.getCandidaturas().stream().anyMatch(c-> c.getAlunoCandidato().equals(alunoLogado));
        if(jaCandidato){
            throw new RuntimeException("Você já está candidado a essa proposta.");
        }
        Candidatura candidatura = new Candidatura();
        candidatura.setAlunoCandidato(alunoLogado);
        candidatura.setProposta(proposta);
        candidatura.setStatus(StatusCandidatura.EM_ANALISE);
        candidatura.setMensagem(mensagem);
        candidaturaRepository.save(candidatura);

        return new CandidaturaDTO(
                candidatura.getId(),
                candidatura.getDataCandidatura(),
                alunoLogado.getNome(),
                alunoLogado.getEmail(),
                alunoLogado.getCurso(),
                mensagem,
                proposta.getId(),
                proposta.getTitulo(),
                proposta.getStatus().name()
        );

    }

    @Transactional
    public List<CandidaturaDTO> listarMinhasCandidaturas(){
        Aluno alunoLogado = (Aluno) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return alunoLogado.getCandidaturas().stream().map(c -> new CandidaturaDTO(
                c.getId(),
                c.getDataCandidatura(),
                c.getAlunoCandidato().getNome(),
                c.getAlunoCandidato().getEmail(),
                c.getAlunoCandidato().getCurso(),
                c.getMensagem(),
                c.getProposta().getId(),
                c.getProposta().getTitulo(),
                c.getStatus().name() // Usar o status da CANDIDATURA aqui
        )).collect(Collectors.toList());
    }

    @Transactional
    public void cancelarCandidatura(Long candidaturaId){
        Aluno alunoLogado = (Aluno) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidatura candidatura = candidaturaRepository.findById(candidaturaId).orElseThrow(() -> new RuntimeException("Candidatura não encontrada"));
        if(!candidatura.getAlunoCandidato().equals(alunoLogado)){
            throw new RuntimeException("Você não tem permissão para cancelar essa candidatura");

        }
        if(candidatura.getStatus() != StatusCandidatura.EM_ANALISE){
            throw new RuntimeException("Só é possível cancelar candidaturas com status 'Em Análise'.");

        }
        candidaturaRepository.delete(candidatura);

    }

    @Transactional
    public void aceitarCandidatura(Long idCandidatura){
        Professor professorLogado = (Professor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidatura candidatura = candidaturaRepository.findById(idCandidatura).orElseThrow(() -> new RuntimeException("Candidatura não encontrada"));
        if(!candidatura.getProposta().getProfessorAutor().equals(professorLogado)){
            throw new RuntimeException("Você não tem permissão para aceitar essa candidatura");

        }
        if(candidatura.getStatus() != StatusCandidatura.EM_ANALISE){
            throw new RuntimeException("Só é possível aceitar candidaturas com status 'Em Análise'.");

        }
        PropostaTCC proposta = candidatura.getProposta();
        if (proposta.getStatus() != StatusTCC.DISPONIVEL) {
            throw new RuntimeException("Esta proposta não está mais disponível para aceitar candidatos.");
        }
        Aluno alunoCandidato = candidatura.getAlunoCandidato();

        proposta.setStatus(StatusTCC.EM_ANDAMENTO);
        proposta.setAlunoResponsavel(alunoCandidato);
        candidatura.setStatus(StatusCandidatura.APROVADA);
        candidaturaRepository.save(candidatura);
        propostaRepository.save(proposta);


        proposta.getCandidaturas().stream()
                .filter(c -> !c.getAlunoCandidato().equals(alunoCandidato) && c.getStatus() == StatusCandidatura.EM_ANALISE)
                .forEach(outraCandidatura -> {
                    outraCandidatura.setStatus(StatusCandidatura.RECUSADA);
                    candidaturaRepository.save(outraCandidatura);
                });




    }




}
