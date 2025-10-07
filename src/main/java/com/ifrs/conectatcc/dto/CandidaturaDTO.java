package com.ifrs.conectatcc.dto;

import com.ifrs.conectatcc.model.TipoCurso;

import java.time.LocalDateTime;

public record CandidaturaDTO(
        Long candidaturaId,
        LocalDateTime dataCandidatura,
        String nome,
        String email,
        TipoCurso tipoCurso,
        String mensagem,
        Long propostaId,
        String tituloProposta,
        String statusProposta
) {}
