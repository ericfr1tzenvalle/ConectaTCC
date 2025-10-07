package com.ifrs.conectatcc.controller;

import com.ifrs.conectatcc.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/aluno")
public class AlunoController {
    @Autowired
    private PropostaRepository propostaRepository;

    //TODO: Implementar os endpoints para o AlunoController conforme o AlunoService
    // Funcionalidades necessarias: //Lembrar de verificar se o aluno esta autenticado e se é o dono da candidatura
    //TODO: Aluno deve poder se candidatar a uma proposta (mudar status para "EM_ANALISE" e adicionar o aluno como candidato)
    //TODO: Aluno deve poder desistir de uma proposta (mudar status para "DISPONIVEL" e remover a associacao do aluno com a proposta)

    // Filtros necessarios: // talvez criar um endpoint /propostas com query params para os filtros ou controller separado pra propostas
    //TODO: Aluno deve poder listar suas propostas candidatas (status = "EM_ANALISE" ou "ACEITA")
    //TODO: Aluno deve listar as propostas disponiveis (status = DISPONIVEL)
    //TODO: Aluno deve poder listar todas as propostas (independente do status)
    //TODO: Aluno deve poder buscar propostas por professor orientador
    //TODO: Aluno deve poder buscar propostas por conhecimento necessario
    //TODO: Aluno deve poder buscar propostas por titulo
    // Configurações da conta
    //TODO: Aluno deve poder editar seus dados (nome, email, telefone e senha) criar ENDPOINT para configurações
    //TODO: Aluno deve poder excluir sua conta (deve remover a associacao com as propostas que ele tinha se candidatado, mudando o status para "DISPONIVEL" e removendo a associacao do aluno com a proposta)
}
