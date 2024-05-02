package com.example.brainBox.configuration.security;

import com.example.brainBox.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String prefix = "Bearer ";
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (token == null || !token.startsWith(prefix)) {
                filterChain.doFilter(request, response);
                return;
            }

            var decodedToken = jwtUtil.verifyTokenAndGetUserData(token.replace(prefix, ""));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(decodedToken.id(), null, List.of(new SimpleGrantedAuthority(decodedToken.role()))));
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}