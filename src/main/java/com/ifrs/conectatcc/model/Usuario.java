/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifrs.conectatcc.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Éric
 */

@Getter@Setter
@Entity // mapeamento como entidade
@Table(name = "usuarios") // define a tabela com nome usuarios
@Inheritance(strategy = InheritanceType.JOINED) // defino que o aluno e professor
//serao duas tabelas diferentes no banco mais ligadas por um FK
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String senha;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String matricula;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;


    @Column(unique = true)
    private String lattes;

    @Column(nullable = false)
    private boolean ativo = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Cria uma "Role" com base no tipo do usuário
        // Exemplo: TipoUsuario.ALUNO -> ROLE_ALUNO
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.tipo.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // pode ajustar se quiser controle de expiração
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // ou pode usar um campo "bloqueado" no futuro
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // normalmente true
    }

    @Override
    public boolean isEnabled() {
        return this.ativo; // usa o campo ativo para habilitar/desabilitar

    }
}
 