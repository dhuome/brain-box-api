package com.example.brainBox.repositories;

import com.example.brainBox.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByNameEn(String nameEn);
}
