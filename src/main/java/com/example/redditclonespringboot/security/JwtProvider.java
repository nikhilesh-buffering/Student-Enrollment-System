package com.example.redditclonespringboot.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtEncoder jwtEncoder;

    public long getJwtExpirationInMillis() {
        return JwtExpirationInMillis;
    }

    @Value("${jwt.expiration.time}")
    private long JwtExpirationInMillis;
    public String generateToken(Authentication authentication){
//        User principal = (User) authentication.getPrincipal();
        return generateTokenWithUsername(authentication.getName());
    }

    public String generateTokenWithUsername(String username) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(JwtExpirationInMillis))
                .subject(username)
                .claim("scope","ROLE_USER")
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
