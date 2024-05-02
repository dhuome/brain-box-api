package com.example.brainBox.repositories;

import com.example.brainBox.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameIgnoreCaseOrEmailIgnoreCaseOrMobileNumber(String username, String email, String mobileNumber);

    Optional<User> findByEmail(String email);
}
