/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifrs.conectatcc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

    @NotBlank(message = "O departamento não pode ser vazio.")
    @Column(name="departamento", nullable = false)
    private String departamento;

    @NotBlank
    @Column(unique = true)
    private String lattes; // link para o curriculo lattes


    @OneToMany(mappedBy = "professorAutor")
    private List<PropostaTCC> propostas = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("PROFESSOR"));
    }

    
    
}
