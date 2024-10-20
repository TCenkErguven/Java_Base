package com.java.base.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JwtTokenProvider {
    @Value("${jwt.secretkey}")
    String secretKey;
    @Value("${jwt.audience}")
    String audience;
    @Value("${jwt.issuer}")
    String issuer;

    public Optional<String> createToken(Long id){
        String token = null;
        Date date = new Date(System.currentTimeMillis() + (1000*60*60*24*5));
        try {
            token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .withClaim("id", id)
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> createToken(Long id, List<String> roles){
        String token = null;
        Date date = new Date(System.currentTimeMillis() + (1000*60*60*24*5));
        try {
            token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .withClaim("id", id)
                    .withClaim("roles", roles)
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

}
