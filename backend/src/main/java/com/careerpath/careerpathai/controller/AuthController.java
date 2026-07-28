package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.UserResponseDTO;
import com.careerpath.careerpathai.dto.auth.AuthResponse;
import com.careerpath.careerpathai.dto.auth.LoginRequest;
import com.careerpath.careerpathai.dto.auth.RegisterRequest;
import com.careerpath.careerpathai.entity.Role;
import com.careerpath.careerpathai.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.careerpath.careerpathai.exception.UserAlreadyExistsException;
import com.careerpath.careerpathai.repository.RoleRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email " + request.getEmail() + " is already registered.");
        }

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setExperienceLevel(request.getExperienceLevel());

        // Always assign STUDENT role on public registration — elevated roles
        // must be granted by an admin after the fact.
        roleRepository.findAll().stream()
                .filter(r -> r.getName().equalsIgnoreCase("STUDENT"))
                .findFirst()
                .ifPresent(user::setRole);

        User saved = userRepository.save(user);
        String roleName = saved.getRole() != null ? saved.getRole().getName() : "STUDENT";
        String token = jwtUtil.generateToken(saved.getEmail(), roleName, saved.getId());

        ApiResponse<AuthResponse> response = new ApiResponse<>(
                true,
                "Registration successful",
                new AuthResponse(token, saved.getId(), saved.getEmail(), roleName)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String roleName = user.getRole() != null ? user.getRole().getName() : "STUDENT";
        String token = jwtUtil.generateToken(user.getEmail(), roleName, user.getId());

        ApiResponse<AuthResponse> response = new ApiResponse<>(
                true,
                "Login successful",
                new AuthResponse(token, user.getId(), user.getEmail(), roleName)
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getMe(
            @AuthenticationPrincipal User user) {

        String roleName = user.getRole() != null ? user.getRole().getName() : null;

        UserResponseDTO dto = new UserResponseDTO(
                user.getId(), user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getExperienceLevel(),
                roleName, user.getCreatedAt()
        );

        return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", dto));
    }
}
