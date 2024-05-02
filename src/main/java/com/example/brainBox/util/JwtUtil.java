package com.example.brainBox.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.brainBox.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.expiration.in.ms}")
    private long JWT_EXPIRATION_IN_MS;

    public String issueToken(User user) {
        Map<String, String> payload = new HashMap<>() {{
            put("username", user.getUsername());
            put("email", user.getEmail());
            put("phoneNumber", user.getMobileNumber());
        }};

        return JWT.create().withSubject(String.valueOf(user.getId())).withPayload(payload).withClaim("roleAr", user.getPrivileges().get(0).getNameAr()).withClaim("roleEn", user.getPrivileges().get(0).getNameEn()).withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS)).sign(Algorithm.HMAC256(JWT_SECRET));
    }

    public DecodedTokenData verifyTokenAndGetUserData(String token) {
        var data = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build().verify(token);
        return new DecodedTokenData(Long.parseLong(data.getSubject()), data.getClaim("roleEn").asString());
    }

    public record DecodedTokenData(Long id, String role) {
    }
}