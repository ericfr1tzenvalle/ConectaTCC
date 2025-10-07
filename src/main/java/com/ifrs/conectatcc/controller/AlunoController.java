package com.ifrs.conectatcc.controller;

import com.ifrs.conectatcc.dto.AtualizarPerfilDTO;
import com.ifrs.conectatcc.dto.PerfilDTO;
import com.ifrs.conectatcc.repository.PropostaRepository;
import com.ifrs.conectatcc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final UserService userService;

    public AlunoController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/perfil")
    public ResponseEntity<PerfilDTO> verPerfil(){
        return ResponseEntity.ok(userService.verPerfil());
    }

    @PutMapping("/perfil")
    public ResponseEntity<PerfilDTO> atualizarPerfil(@Valid @RequestBody AtualizarPerfilDTO atualizarPerfilDTO){
        return ResponseEntity.ok(userService.atualizarPerfil(atualizarPerfilDTO));
    }



}
