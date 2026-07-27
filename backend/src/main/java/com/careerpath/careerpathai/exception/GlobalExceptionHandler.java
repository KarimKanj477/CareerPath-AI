package com.careerpath.careerpathai.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import com.careerpath.careerpathai.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Validation failed", errors));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleRoleNotFound(RoleNotFoundException ex) {

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleRoleAlreadyExists(RoleAlreadyExistsException ex) {

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(CareerNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleCareerNotFound(
            CareerNotFoundException exception) {

        ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CareerAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleCareerAlreadyExists(
            CareerAlreadyExistsException exception) {

        ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // FIX (new): Skill lookups now throw a proper custom exception —
    // this is the handler that turns it into a clean 404 instead of
    // Spring's default error page.
    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleSkillNotFound(
            SkillNotFoundException exception) {

        ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // FIX (new): catch-all so any exception we didn't anticipate (DB
    // hiccup, unexpected constraint violation, etc.) returns our
    // ApiResponse shape with a generic message instead of Spring's
    // default whitelabel error page, which can leak a stack trace.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception exception) {

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                "An unexpected error occurred. Please try again later.",
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
