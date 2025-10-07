package com.ifrs.conectatcc.dto;

import java.util.List;

public record PropostaDetalheDTO(
        Long id,
        String titulo,
        String descricao,
        String status,
        String nomeProfessor,
        String nomeAlunoResponsavel, // null se não tiver
        List<CandidaturaDTO> candidaturas // se quiser mostrar
) {}