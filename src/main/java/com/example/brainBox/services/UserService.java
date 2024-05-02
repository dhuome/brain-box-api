package com.example.brainBox.services;

import com.example.brainBox.dtos.CreateUserRequest;
import com.example.brainBox.entities.User;
import com.example.brainBox.exceptions.BadRequestException;
import com.example.brainBox.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.brainBox.common.constants.constants.ErrorMessages.USER_EXISTS;
import static com.example.brainBox.common.constants.constants.ErrorMessages.USER_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PrivilegeService privilegeService;

    public void createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByUsernameIgnoreCaseOrEmailIgnoreCaseOrMobileNumber(createUserRequest.getUsername(), createUserRequest.getEmail(), createUserRequest.getMobileNumber())) {
            throw new BadRequestException(USER_EXISTS);
        }

        userRepository.save(User.maptoUser(createUserRequest, privilegeService.getUserPrivilege(), passwordEncoder.encode(createUserRequest.getPassword())));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(USER_NOT_EXISTS));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException(USER_NOT_EXISTS));
    }

    public User getUserIfExist(Authentication authentication) {
        if (authentication != null) {
            return getUserById((Long) authentication.getPrincipal());
        }
        return null;
    }
}