package com.ifrs.conectatcc.dto;

import com.ifrs.conectatcc.model.TipoCurso;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlunoRegistroDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha,
        @NotBlank String matricula,
        @NotNull TipoCurso curso,
        String lattes // agora opcional
) {}
