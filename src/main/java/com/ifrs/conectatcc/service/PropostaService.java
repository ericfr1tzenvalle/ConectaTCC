package com.ifrs.conectatcc.service;

import com.ifrs.conectatcc.dto.CandidaturaDTO;
import com.ifrs.conectatcc.dto.CriarPropostaDTO;
import com.ifrs.conectatcc.dto.PropostaDTO;
import com.ifrs.conectatcc.dto.PropostaDetalheDTO;
import com.ifrs.conectatcc.model.*;
import com.ifrs.conectatcc.repository.ProfessorRepository;
import com.ifrs.conectatcc.repository.PropostaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PropostaService {
    private final PropostaRepository propostaRepository;
    private final ProfessorRepository professorRepository;
    //Todos autowired foram retirados por não serem uma boa prática.
    public PropostaService(PropostaRepository propostaRepository , ProfessorRepository professorRepository) {
        this.propostaRepository = propostaRepository;
        this.professorRepository = professorRepository;
    }

    @Transactional
    public List<PropostaDTO> listarTodasPropostas(){
        List<PropostaTCC> todasPropostas = propostaRepository.findAll();
        return todasPropostas.stream().map(p-> new PropostaDTO(p.getId(),p.getTitulo(),p.getDescricao(),p.getStatus(),p.getProfessorAutor().getNome())).toList();
    }
    @Transactional
    public List<PropostaDTO> listarPropostasDoProfessorLogado() {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Professor professorLogado = professorRepository.findById(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        return professorLogado.getPropostas()
                .stream()
                .map(p -> new PropostaDTO(p.getId(), p.getTitulo(), p.getDescricao(), p.getStatus(), professorLogado.getNome()))
                .toList();
    }

    @Transactional
    public PropostaDTO criarProposta(CriarPropostaDTO criarPropostaDTO){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Professor professorAutor = professorRepository.findById(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado no banco de dados."));

        PropostaTCC proposta = new PropostaTCC();
        proposta.setTitulo(criarPropostaDTO.titulo());
        proposta.setDescricao(criarPropostaDTO.descricao());
        proposta.setProfessorAutor(professorAutor);
        proposta.setStatus(StatusTCC.DISPONIVEL);
        PropostaTCC salva = propostaRepository.save(proposta);
        return new PropostaDTO(
                salva.getId(),
                salva.getTitulo(),
                salva.getDescricao(),
                salva.getStatus(),
                salva.getProfessorAutor().getNome()
        );
    }


    @Transactional
    public PropostaDTO atualizarProposta(Long id, @Valid PropostaTCC propostaAtualizada){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Professor professorLogado = professorRepository.findById(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        PropostaTCC propostaExistente = propostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposta não encontrada"));

        if(!propostaExistente.getProfessorAutor().equals(professorLogado)){
            throw new RuntimeException("Você não tem permissão para editar essa proposta");
        }
        if (propostaExistente.getStatus() != StatusTCC.DISPONIVEL) {
            throw new RuntimeException("Só é possível editar propostas com status 'Disponível'.");
        }

        propostaExistente.setTitulo(propostaAtualizada.getTitulo());
        propostaExistente.setDescricao(propostaAtualizada.getDescricao());
        PropostaTCC salva = propostaRepository.save(propostaExistente);

        return new PropostaDTO(
                salva.getId(),
                salva.getTitulo(),
                salva.getDescricao(),
                salva.getStatus(),
                salva.getProfessorAutor().getNome()
        );
    }

    @Transactional
    public void deletarProposta(Long id){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Professor professorLogado = professorRepository.findById(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        PropostaTCC propostaExistente = propostaRepository.findById(id).orElseThrow(() -> new RuntimeException("Proposta não encontrada"));
        if(!propostaExistente.getProfessorAutor().equals(professorLogado)){
            throw new RuntimeException("Você não tem permissão para deletar essa proposta");
        }
        if(propostaExistente.getStatus() != StatusTCC.DISPONIVEL){
            throw new RuntimeException("Só é possível deletar propostas com status 'Disponível'.");
        }
        propostaRepository.delete(propostaExistente);

    }

    @Transactional
    public PropostaDetalheDTO buscarPorId(Long id){
        PropostaTCC proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposta não encontrada"));

        List<CandidaturaDTO> candidaturasDTO = proposta.getCandidaturas().stream()
                .map(c -> new CandidaturaDTO(
                        c.getId(),
                        c.getDataCandidatura(),
                        c.getAlunoCandidato().getNome(),
                        c.getAlunoCandidato().getEmail(),
                        c.getAlunoCandidato().getCurso(),
                        c.getMensagem(),
                        proposta.getId(),
                        proposta.getTitulo(),
                        proposta.getStatus().name()
                ))
                .toList();

        return new PropostaDetalheDTO(
                proposta.getId(),
                proposta.getTitulo(),
                proposta.getDescricao(),
                proposta.getStatus().name(),
                proposta.getProfessorAutor().getNome(),
                proposta.getAlunoResponsavel() != null ? proposta.getAlunoResponsavel().getNome() : null,
                candidaturasDTO
        );
    }



}
