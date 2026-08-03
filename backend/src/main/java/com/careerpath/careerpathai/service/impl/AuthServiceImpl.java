package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.dto.auth.AuthResponse;
import com.careerpath.careerpathai.dto.auth.LoginRequest;
import com.careerpath.careerpathai.dto.auth.RegisterRequest;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.UserAlreadyExistsException;
import com.careerpath.careerpathai.repository.RoleRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.security.JwtUtil;
import com.careerpath.careerpathai.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
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

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email " + request.getEmail() + " is already registered.");
        }

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setExperienceLevel(request.getExperienceLevel());

        roleRepository.findAll().stream()
                .filter(role -> role.getName().equalsIgnoreCase("STUDENT"))
                .findFirst()
                .ifPresent(user::setRole);

        User saved = userRepository.save(user);
        String roleName = saved.getRole() != null ? saved.getRole().getName() : "STUDENT";
        String token = jwtUtil.generateToken(saved.getEmail(), roleName, saved.getId());

        return new AuthResponse(token, saved.getId(), saved.getEmail(), roleName);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String roleName = user.getRole() != null ? user.getRole().getName() : "STUDENT";
        String token = jwtUtil.generateToken(user.getEmail(), roleName, user.getId());

        return new AuthResponse(token, user.getId(), user.getEmail(), roleName);
    }

    @Override
    public User getAuthenticatedUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }
}
