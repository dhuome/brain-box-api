package com.example.brainBox.services;

import com.example.brainBox.entities.Privilege;
import com.example.brainBox.exceptions.BadRequestException;
import com.example.brainBox.repositories.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.brainBox.common.constants.constants.ErrorMessages.PRIVILEGE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PrivilegeService {
    private final PrivilegeRepository privilegeRepository;

    public Privilege getUserPrivilege() {
        return privilegeRepository.findByNameEn("USER").orElseThrow(() -> new BadRequestException(PRIVILEGE_NOT_FOUND));
    }
}
