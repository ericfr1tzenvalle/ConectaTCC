package com.ifrs.conectatcc.dto;

import com.ifrs.conectatcc.model.StatusTCC;

public record PropostaDTO(Long id, String titulo, String descricao, StatusTCC status, String nome) {}
