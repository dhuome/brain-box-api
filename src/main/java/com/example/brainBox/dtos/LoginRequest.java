package com.example.brainBox.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.example.brainBox.common.constants.constants.Defaults.PASSWORD_MAX_LENGTH;
import static com.example.brainBox.common.constants.constants.Defaults.PASSWORD_MIN_LENGTH;
import static com.example.brainBox.common.constants.constants.ErrorMessages.*;

@Data
public class LoginRequest {
    @NotNull(message = NULL_FILED)
    @Email(message = EMAIL_PATTERN)
    private String email;

    @NotBlank(message = BLANK_FILED)
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = MIN_MAX_PASSWORD_LENGTH)
    private String password;
}
