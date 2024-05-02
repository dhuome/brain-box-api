package com.example.brainBox.controllers;

import com.example.brainBox.dtos.LoginRequest;
import com.example.brainBox.dtos.LoginResponse;
import com.example.brainBox.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.brainBox.common.constants.constants.Endpoints.BASE_AUTH_URL;
import static com.example.brainBox.common.constants.constants.Endpoints.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL + BASE_AUTH_URL)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
