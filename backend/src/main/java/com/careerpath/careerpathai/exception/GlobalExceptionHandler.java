package com.careerpath.careerpathai.exception;

import com.careerpath.careerpathai.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleRoleNotFound(RoleNotFoundException ex) {

        ApiResponse<Object> response = new ApiResponse<>(false, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleRoleAlreadyExists(RoleAlreadyExistsException ex) {

        ApiResponse<Object> response = new ApiResponse<>(false, ex.getMessage(), null);

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

    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleSkillNotFound(SkillNotFoundException exception) {

        ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(SkillAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleSkillAlreadyExists(SkillAlreadyExistsException exception) {

        ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(), null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>handleUserNotFound(UserNotFoundException exception){
        ApiResponse<Object> response= new ApiResponse<>(false,exception.getMessage(),null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>handleUserAlreadyExists(UserAlreadyExistsException exception){
        ApiResponse<Object> response= new ApiResponse<>(false,exception.getMessage(),null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(
            BadCredentialsException exception) {

        ApiResponse<Void> response = new ApiResponse<>(false, exception.getMessage(), null
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(UserSkillNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserSkillNotFound(
            UserSkillNotFoundException exception) {

        ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserSkillAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserSkillAlreadyExists(
            UserSkillAlreadyExistsException exception) {

        ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(), null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(RoadmapNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleRoadmapNotFound(
            RoadmapNotFoundException exception) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        exception.getMessage(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }





}