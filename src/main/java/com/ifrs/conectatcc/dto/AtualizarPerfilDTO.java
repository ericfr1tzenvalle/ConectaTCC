package com.ifrs.conectatcc.dto;

import jakarta.validation.constraints.NotBlank;

public record AtualizarPerfilDTO(
        @NotBlank String nome,
        String lattes
        // A troca de senha geralmente é um fluxo separado e mais seguro.
) {
}
