package com.example.brainBox.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import static com.example.brainBox.common.constants.constants.Defaults.*;
import static com.example.brainBox.common.constants.constants.ErrorMessages.*;
import static com.example.brainBox.common.constants.constants.Regex.MOBIL_NUMBER_REGEX;

@Data
public class CreateUserRequest {
    @NotBlank(message = BLANK_FILED)
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH, message = MIN_MAX_USERNAME_LENGTH)
    private String username;

    @NotNull(message = NULL_FILED)
    @Pattern(regexp = MOBIL_NUMBER_REGEX, message = MOBILE_NUMBER_PATTERN)
    private String mobileNumber;

    @NotNull(message = NULL_FILED)
    @Email(message = EMAIL_PATTERN)
    private String email;

    @NotBlank(message = BLANK_FILED)
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = MIN_MAX_PASSWORD_LENGTH)
    private String password;
}
