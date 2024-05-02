package com.example.brainBox.exceptions;

import com.example.brainBox.dtos.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;

import static com.example.brainBox.common.constants.constants.ErrorMessages.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleServerError(Exception ex) {
        log.error("Error:", ex);
        return ResponseEntity.internalServerError().body(new ErrorResponse(List.of(INTERNAL_SERVER_ERROR)));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Error:", ex);
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream().map((fieldError) -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage())).toList();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessages));
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Error:", ex);
        List<String> errorMessages = ex.getAllValidationResults().stream().flatMap(result -> result.getResolvableErrors().stream()).map(MessageSourceResolvable::getDefaultMessage).toList();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessages));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName().toLowerCase();
        return ResponseEntity.badRequest().body(new ErrorResponse(List.of(errorMessage)));
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        log.error("Error:", ex);
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessages()));
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
        log.error("Error:", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(List.of(ex.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Error:", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(List.of(NO_AUTHORITY)));
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Error:", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(List.of(EMAIL_OR_PASSWORD_INCORRECT)));
    }
}