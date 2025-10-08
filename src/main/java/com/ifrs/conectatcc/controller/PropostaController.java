package com.ifrs.conectatcc.controller;

import com.ifrs.conectatcc.dto.CriarPropostaDTO;
import com.ifrs.conectatcc.dto.PropostaDTO;
import com.ifrs.conectatcc.dto.PropostaDetalheDTO;
import com.ifrs.conectatcc.model.Professor;
import com.ifrs.conectatcc.model.PropostaTCC;
import com.ifrs.conectatcc.service.PropostaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaService propostaService;

    public PropostaController(PropostaService propostaService){
        this.propostaService = propostaService;
    }

    //TODO: criar filtros para propostas posteriormente no Service

    @PostMapping("/criar")
    public ResponseEntity<PropostaDTO> criar(@RequestBody CriarPropostaDTO propostaDTO){
        PropostaDTO propostaCriada = propostaService.criarProposta(propostaDTO);
        return ResponseEntity.ok(propostaCriada);

    }

    @GetMapping("/minhas/{id}")
    public ResponseEntity<PropostaDetalheDTO> buscarPorId(@PathVariable Long id){
        PropostaDetalheDTO proposta = propostaService.buscarPorId(id);
        return ResponseEntity.ok(proposta);
    }


    @GetMapping("/todas")
    public ResponseEntity<List<PropostaDTO>> listarPropostas(){
        List<PropostaDTO> propostas = propostaService.listarTodasPropostas();
        return ResponseEntity.ok(propostas);
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<PropostaDTO>> listarPropostasDoProfessorLogado() {
        List<PropostaDTO> propostas = propostaService.listarPropostasDoProfessorLogado();
        return ResponseEntity.ok(propostas);
    }
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PropostaDTO> atualizarProposta(@PathVariable Long id, @RequestBody PropostaTCC propostaAtualizada) {
        PropostaDTO propostaAtualizadaNoBanco = propostaService.atualizarProposta(id, propostaAtualizada);
        return ResponseEntity.ok(propostaAtualizadaNoBanco);
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<PropostaDTO> deletarProposta(@PathVariable Long id) {
        propostaService.deletarProposta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/concluir/{id}")
    public ResponseEntity<PropostaDTO> concluirProposta(@PathVariable Long id){
        PropostaDTO propostaConcluida = propostaService.concluirProposta(id);
        return ResponseEntity.ok(propostaConcluida);
    }

}
