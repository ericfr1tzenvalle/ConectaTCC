/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ifrs.conectatcc.service;

import com.ifrs.conectatcc.model.Aluno;
import com.ifrs.conectatcc.model.Professor;
import com.ifrs.conectatcc.repository.AlunoRepository;
import com.ifrs.conectatcc.repository.ProfessorRepository;
import com.ifrs.conectatcc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 *
 * @author Éric
 */
@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Aluno cadastrarAluno(Aluno aluno){
        //Criptografar a senha
        String senhaCriptografada = passwordEncoder.encode(aluno.getSenha());
        aluno.setSenha(senhaCriptografada);
        //Salvar no banco e retornar
        return alunoRepository.save(aluno);
    }
    public Professor cadastrarProfessor(Professor professor){
        String senhaCriptografada = passwordEncoder.encode(professor.getSenha());
        professor.setSenha(senhaCriptografada);
        
        return professorRepository.save(professor);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }

}
    

    
 
