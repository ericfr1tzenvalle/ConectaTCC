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

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Éric
 */

@Getter
@Setter
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

    @NotBlank(message = "O nome não pode ser vazio.")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O email não pode ser vazio.")
    @Email(message = "Formato de email inválido.")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "A senha não pode ser vazia.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    @Column(name = "senha", nullable = false, length = 100)
    private String senha;

    @NotBlank(message = "A matrícula não pode ser vazia.")
    @Column(name="matricula", nullable = false, unique = true)
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
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.tipo.name())); // Voltei com essa config
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
 