/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ifrs.conectatcc.controller;

import com.ifrs.conectatcc.dto.LoginDTO;
import com.ifrs.conectatcc.dto.TokenDTO;
import com.ifrs.conectatcc.model.Aluno;
import com.ifrs.conectatcc.model.Professor;
import com.ifrs.conectatcc.model.Usuario;
import com.ifrs.conectatcc.service.TokenService;
import com.ifrs.conectatcc.service.UserService;
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

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register/aluno")
    public ResponseEntity<Aluno> registrarAluno(@RequestBody Aluno aluno) {
        Aluno novoAluno = userService.cadastrarAluno(aluno);
        return ResponseEntity.ok(novoAluno);
    }

    @PostMapping("/register/professor")
    public ResponseEntity<Professor> registrarProfessor(@RequestBody Professor professor) {
        Professor novoProfessor = userService.cadastrarProfessor(professor);
        return ResponseEntity.ok(novoProfessor);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        String token = tokenService.gerarToken(usuarioLogado);
        return ResponseEntity.ok(new TokenDTO(token));
    }

}
