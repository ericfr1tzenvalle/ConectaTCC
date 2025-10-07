package com.ifrs.conectatcc.controller;

import com.ifrs.conectatcc.repository.PropostaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    @Autowired
    private PropostaRepository propostaRepository;

    //TODO: Implementar ROTAS aqui de acordo com o Service ProfessorService
    //TODO: O professor deve ser capaz de criar, editar e deletar propostas de TCC
    //TODO: O professor deve ser capaz de listar suas propostas de TCC
    //TODO: O professor deve ser capaz de buscar propostas com base em critérios como título, área de conhecimento, etc.
    //TODO: O professor deve ser capaz de ver as candidaturas dos alunos para suas propostas
    //TODO: O professor deve ser capaz de aprovar ou rejeitar candidaturas dos alunos para suas propostas, e quando aprovar, o status da proposta deve ser atualizado para "Em Andamento" e o
    // aluno deve ser notificado e atribuido ao TCC.

    //TODO: O professor deve ser capaz de ver o progresso dos TCCs que ele está supervisionando (OPCICIONAL)
    //TODO: O professor deve ser capaz de avaliar e fornecer feedback sobre os TCCs que ele está supervisionando (OPCIONAL)
    //TODO: O professor deve ser capaz de comunicar-se com os alunos que estão trabalhando em seus TCCs (OPCIONAL)

    //TODO: O professor deve ser capaz de editar seu perfil bem como ver suas informações
    //TODO: O professor deve ser capaz de ver notificações relacionadas às suas propostas e TCCs supervisionados (OPCIONAL)
    //TODO: O professor deve ser capaz de ver um dashboard com uma visão geral de suas propostas, TCCs supervisionados e outras informações relevantes
    //TODO: O professor deve ser capaz de alterar sua senha e outras configurações de conta
    //TODO: o professor deve ser capaz de deletar sua conta.


}
