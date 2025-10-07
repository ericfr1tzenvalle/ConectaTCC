/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ifrs.conectatcc.service;

import com.ifrs.conectatcc.dto.AlunoRegistroDTO;
import com.ifrs.conectatcc.dto.AtualizarPerfilDTO;
import com.ifrs.conectatcc.dto.PerfilDTO;
import com.ifrs.conectatcc.dto.ProfessorRegistroDTO;
import com.ifrs.conectatcc.model.Aluno;
import com.ifrs.conectatcc.model.Professor;
import com.ifrs.conectatcc.model.TipoUsuario;
import com.ifrs.conectatcc.model.Usuario;
import com.ifrs.conectatcc.repository.AlunoRepository;
import com.ifrs.conectatcc.repository.ProfessorRepository;
import com.ifrs.conectatcc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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


    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(ProfessorRepository professorRepository,
                       AlunoRepository alunoRepository,
                       UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder) {
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PerfilDTO verPerfil(){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new PerfilDTO(
                usuarioLogado.getId(),
                usuarioLogado.getNome(),
                usuarioLogado.getEmail(),
                usuarioLogado.getMatricula(),
                usuarioLogado.getLattes()
        );
    }

    public PerfilDTO atualizarPerfil(AtualizarPerfilDTO atualizarPerfilDTO){
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName(); //Pega o username pois o email é unico
        Usuario usuarioLogado = usuarioRepository.findByEmail(emailUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioLogado.setNome(atualizarPerfilDTO.nome());
        usuarioLogado.setLattes(atualizarPerfilDTO.lattes());
        usuarioRepository.save(usuarioLogado);

        return new PerfilDTO(
                usuarioLogado.getId(),
                usuarioLogado.getNome(),
                usuarioLogado.getEmail(),
                usuarioLogado.getMatricula(),
                usuarioLogado.getLattes()
        );

    }

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