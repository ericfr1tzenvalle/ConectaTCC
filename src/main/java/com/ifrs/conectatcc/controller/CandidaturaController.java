package com.ifrs.conectatcc.controller;

import com.ifrs.conectatcc.dto.CandidaturaDTO;
import com.ifrs.conectatcc.dto.CriarCandidaturaRequest;
import com.ifrs.conectatcc.service.CandidaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidaturas")
public class CandidaturaController {
    private final CandidaturaService candidaturaService;

    public CandidaturaController(CandidaturaService candidaturaService) {
        this.candidaturaService = candidaturaService;
    }

    @PostMapping
    public ResponseEntity<CandidaturaDTO> criarCandidatura(@RequestBody CriarCandidaturaRequest request) {
        CandidaturaDTO candidaturaCriada = candidaturaService.criarCandidatura(request.propostaId(), request.mensagem());
        return ResponseEntity.ok(candidaturaCriada);
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<CandidaturaDTO>> listarMinhasCandidaturas() {
        List<CandidaturaDTO> candidaturas = candidaturaService.listarMinhasCandidaturas();
        return ResponseEntity.ok(candidaturas);
    }

    @DeleteMapping("/minhas/{id}")
    public ResponseEntity<Void> cancelarMinhaCandidatura(@PathVariable Long id){
        candidaturaService.cancelarCandidatura(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/candidaturas/{id}")
    public ResponseEntity<Void> aceitarCandidatura(@PathVariable Long id){
        candidaturaService.aceitarCandidatura(id);
        return ResponseEntity.noContent().build();
    }




}
