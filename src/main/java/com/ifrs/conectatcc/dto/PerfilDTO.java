package com.ifrs.conectatcc.dto;

public record PerfilDTO(
        Long id,
        String nome,
        String email,
        String matricula,
        String lattes
) {}