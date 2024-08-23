package com.github.ericknathan.tasks.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.github.ericknathan.tasks.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {
    @Value("${api.token.secret}")
    private String tokenSecret;

    @Value("${api.token.issuer}")
    private String tokenIssuer;

    public String generateToken(UserModel user){
        try {
            return JWT.create()
                    .withIssuer(tokenIssuer)
                    .withSubject(user.getEmail())
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                    .sign(Algorithm.HMAC256(tokenSecret));
        } catch (JWTCreationException e){
            throw new RuntimeException("Error generating token", e);
        }
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(tokenSecret))
                .withIssuer(tokenIssuer)
                .build()
                .verify(token)
                .getSubject();
    }
}