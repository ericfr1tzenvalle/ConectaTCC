/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifrs.conectatcc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 *
 * @author Éric
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Entity
@Table(name = "aluno")
public class Aluno extends Usuario{
    @Column(name="curso", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCurso curso;
    @OneToMany(mappedBy = "candidato")
    private List<Candidatura> candidaturas;

}
