package com.example.brainBox.controllers;

import com.example.brainBox.dtos.CreateUserRequest;
import com.example.brainBox.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.brainBox.common.constants.constants.Endpoints.BASE_URL;
import static com.example.brainBox.common.constants.constants.Endpoints.USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL + USERS)
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        userService.createUser(createUserRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
