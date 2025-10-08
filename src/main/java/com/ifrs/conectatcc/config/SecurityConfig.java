package com.ifrs.conectatcc.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // Libera o console do H2
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        // Libera os endpoints de autenticação/ como login e register
                        .requestMatchers(mvcMatcherBuilder.pattern("/auth/**")).permitAll()

                        // Regras do Professor
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/propostas/criar")).hasRole("PROFESSOR")
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/propostas/atualizar/**")).hasRole("PROFESSOR")
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/propostas/deletar/**")).hasRole("PROFESSOR")
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/propostas/minhas/**")).hasRole("PROFESSOR")
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/professor/candidaturas/{id}/aceitar")).hasRole("PROFESSOR")
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/{id}/concluir")).hasRole("PROFESSOR")
                        .requestMatchers(mvcMatcherBuilder.pattern("/professor/**")).hasRole("PROFESSOR")

                        // Regras do Aluno
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/candidaturas")).hasRole("ALUNO")
                        .requestMatchers(mvcMatcherBuilder.pattern("/candidaturas/minhas/**")).hasRole("ALUNO")
                        .requestMatchers(mvcMatcherBuilder.pattern("/aluno/**")).hasRole("ALUNO")

                        // Qualquer usuário autenticado
                        .requestMatchers(mvcMatcherBuilder.pattern("/propostas/todas")).authenticated()

                        // Todas as outras requisições exigem autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())); // Necessário para o H2 Console

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}