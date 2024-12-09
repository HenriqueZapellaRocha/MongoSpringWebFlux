package com.example.mongospringwebflux.service.services.securityServices;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Método para gerar o token JWT de forma reativa
    public Mono<String> generateToke(UserEntity userEntity) {
        return Mono.defer(() -> {
            try {
                Algorithm algorithm = Algorithm.HMAC256("VERYCRAZY");

                // Gera o token de forma síncrona, mas encapsula em Mono.defer para garantir comportamento reativo
                String token = JWT.create()
                        .withIssuer("aut.api")
                        .withSubject(userEntity.getLogin())
                        .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                        .sign(algorithm);

                return Mono.just(token); // Retorna o token gerado
            } catch (Exception e) {
                return Mono.error(new RuntimeException("Erro ao gerar o token", e)); // Em caso de erro, retorna um erro Mono
            }
        });
    }


    public Mono<String> validateToke(String token) {
        return Mono.defer(() -> {
            try {
                Algorithm algorithm = Algorithm.HMAC256("VERYCRAZY");
                DecodedJWT jwt = JWT.require(algorithm)
                        .withIssuer("aut.api")
                        .build()
                        .verify(token);

                String login = jwt.getSubject();
                return Mono.just(login);
            } catch (Exception e) {
                return Mono.error( new RuntimeException("Erro ao validar o token", e) );
            }
        });
    }
}

