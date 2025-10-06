/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifrs.conectatcc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="professor")
public class Professor extends Usuario{
    @Column(name="departamento", nullable = false)
    private String departamento;
    @OneToMany(mappedBy = "professorAutor")
    private List<PropostaTCC> propostas;

    
    
}
