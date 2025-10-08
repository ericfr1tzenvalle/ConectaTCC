/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ifrs.conectatcc.controller;

import com.ifrs.conectatcc.dto.*;
import com.ifrs.conectatcc.model.Aluno;
import com.ifrs.conectatcc.model.Professor;
import com.ifrs.conectatcc.model.Usuario;
import com.ifrs.conectatcc.service.TokenService;
import com.ifrs.conectatcc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;

/**
 *
 * @author Éric
 */
@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, TokenService tokenService, AuthenticationManager authenticationManager){
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    //TODO: Implementar validações e tratamento de erros

    @PostMapping("/register/aluno")
    public ResponseEntity<PerfilDTO> registrarAluno(@Valid @RequestBody AlunoRegistroDTO alunoRegistroDTO) {
        PerfilDTO novoAluno = userService.cadastrarAluno(alunoRegistroDTO);
        return ResponseEntity.ok(novoAluno);
    }

    @PostMapping("/register/professor")
    public ResponseEntity<PerfilDTO> registrarProfessor(@Valid @RequestBody ProfessorRegistroDTO professorRegistroDTO) {
        PerfilDTO novoProfessor = userService.cadastrarProfessor(professorRegistroDTO);
        return ResponseEntity.ok(novoProfessor);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
            String token = tokenService.gerarToken(usuarioLogado);
            return ResponseEntity.ok(new TokenDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new TokenDTO("Credenciais inválidas"));
        }
    }

}
