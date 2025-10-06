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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Éric
 */
@Getter@Setter
@Entity
@Table(name="proposta")
public class PropostaTCC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="titulo",nullable = false,unique = true)
    private String titulo;
    @Column(name="descricao", nullable = false)
    private String descricao;
    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTCC status = StatusTCC.DISPONIVEL;
    @ManyToOne
    @JoinColumn(name ="professor_autor_id")
    private Professor professorAutor;
    @OneToOne
    @JoinColumn(name ="aluno_responsavel_id")
    private Aluno alunoResponsavel;
    @OneToMany(mappedBy = "proposta")
    List<Candidatura> candidaturas;
    
}
