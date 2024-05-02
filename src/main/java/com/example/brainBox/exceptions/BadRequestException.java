package com.example.brainBox.exceptions;

import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class BadRequestException extends RuntimeException {
    private List<String> messages;

    public BadRequestException(String message) {
        this.messages = List.of(message);
    }

    public BadRequestException(Set<ConstraintViolation<Object>> violations) {
        this.messages = violations.stream().map(ConstraintViolation::getMessage).toList();
    }
}
