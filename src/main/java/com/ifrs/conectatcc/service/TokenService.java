package com.ifrs.conectatcc.service;

import com.ifrs.conectatcc.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Service // Indica que essa classe é um "serviço" do Spring (componente de lógica de negócio)
public class TokenService {

    // Pega o valor da chave secreta definida no application.properties (jwt.secret=algum_valor)
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Gera um token JWT para o usuário.
     * Esse token serve para autenticar o usuário nas próximas requisições sem precisar logar de novo.
     */
    public String gerarToken(Usuario usuario) {

        // Data e hora atuais (quando o token foi criado)
        Date agora = new Date();

        // Define quando o token vai expirar (1 dia depois de "agora")
        // 86400000 milissegundos = 24 horas
        Date dataExpiracao = new Date(agora.getTime() + 86400000);

        String roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Cria a chave secreta que será usada pra "assinar" o token.
        // Essa chave garante que só o servidor consegue validar se o token é verdadeiro.
        SecretKey chave = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        // Cria o token propriamente dito
        return Jwts.builder()
                // Quem está emitindo o token (pode ser o nome da API, app, etc.)
                .setIssuer("API ConectaTCC")

                // Define o "subject", ou seja, o dono do token — aqui é o e-mail do usuário.
                // Isso é o que identifica de quem é esse token.
                .setSubject(usuario.getEmail())

                //Fix: definir a role
                .claim("roles", roles)

                // Data de criação do token
                .setIssuedAt(agora)

                // Data de expiração do token
                .setExpiration(dataExpiracao)

                // "Assina" o token com a chave e o algoritmo de segurança HS256 (HMAC + SHA-256)
                // Isso impede que alguém consiga falsificar o token.
                .signWith(chave, SignatureAlgorithm.HS256)

                // Gera a string final (token JWT em formato compacto)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            SecretKey chave = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parserBuilder()
                    .setSigningKey(chave)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
