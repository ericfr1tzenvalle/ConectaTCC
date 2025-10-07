package com.ifrs.conectatcc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfessorRegistroDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha,
        @NotBlank String matricula,
        @NotBlank String departamento,
        String lattes // opcional, assim como no aluno
) {}
