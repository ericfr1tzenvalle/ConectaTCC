package com.ifrs.conectatcc.controller;

import com.ifrs.conectatcc.dto.AtualizarPerfilDTO;
import com.ifrs.conectatcc.dto.PerfilDTO;
import com.ifrs.conectatcc.repository.PropostaRepository;
import com.ifrs.conectatcc.service.CandidaturaService;
import com.ifrs.conectatcc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    private final CandidaturaService candidaturaService;
    private final UserService userService;

    public ProfessorController(CandidaturaService candidaturaService, UserService userService) {
        this.candidaturaService = candidaturaService;
        this.userService = userService;
    }
    @PostMapping("/candidaturas/{id}/aceitar")
    public ResponseEntity<Void> aceitarCandidatura(@PathVariable Long id){
        candidaturaService.aceitarCandidatura(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/perfil")
    public ResponseEntity<PerfilDTO> verPerfil() {
        return ResponseEntity.ok(userService.verPerfil());
    }

    @PutMapping("/perfil")
    public ResponseEntity<PerfilDTO> atualizarPerfil(@Valid @RequestBody AtualizarPerfilDTO atualizarPerfilDTO) {
        return ResponseEntity.ok(userService.atualizarPerfil(atualizarPerfilDTO));
    }



}
