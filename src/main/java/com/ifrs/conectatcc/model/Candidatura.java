/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifrs.conectatcc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Éric
 */
@Getter@Setter
@Entity
@Table(name ="candidatura")
public class Candidatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataCandidatura;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCandidatura status;


    private String mensagem;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name="aluno_candidato_id")
    private Aluno alunoCandidato;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name="proposta_tcc_id")
    private PropostaTCC proposta;
    
    
}
