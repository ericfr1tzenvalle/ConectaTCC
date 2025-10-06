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
    @Column(name="data", nullable = false)
    private LocalDate dataCandidatura;
    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCandidatura status;
    @Column(name="mensagem")
    private String mensagem;
    @ManyToOne
    @JoinColumn(name="aluno_id")
    private Aluno candidato;
    @ManyToOne
    @JoinColumn(name="proposta_tcc_id")
    private PropostaTCC proposta;
    
    
}
