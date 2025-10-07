/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ifrs.conectatcc.service;

import com.ifrs.conectatcc.dto.AlunoRegistroDTO;
import com.ifrs.conectatcc.dto.ProfessorRegistroDTO;
import com.ifrs.conectatcc.model.Aluno;
import com.ifrs.conectatcc.model.Professor;
import com.ifrs.conectatcc.model.TipoUsuario;
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

    public Aluno cadastrarAluno(AlunoRegistroDTO dto){
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setSenha(passwordEncoder.encode(dto.senha()));
        aluno.setMatricula(dto.matricula());
        aluno.setCurso(dto.curso());
        aluno.setTipo(TipoUsuario.ALUNO);
        //aluno.setAtivo(true);
        return alunoRepository.save(aluno);
    }
    public Professor cadastrarProfessor(ProfessorRegistroDTO dto){
        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setSenha(passwordEncoder.encode(dto.senha()));
        professor.setMatricula(dto.matricula());
        professor.setDepartamento(dto.departamento());
        professor.setLattes(dto.lattes());
        professor.setTipo(TipoUsuario.PROFESSOR);
        professor.setAtivo(true);
        return professorRepository.save(professor);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }

}
    

    
 
