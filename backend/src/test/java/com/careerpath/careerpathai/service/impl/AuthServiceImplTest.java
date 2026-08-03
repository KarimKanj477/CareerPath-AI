package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.dto.auth.AuthResponse;
import com.careerpath.careerpathai.dto.auth.LoginRequest;
import com.careerpath.careerpathai.dto.auth.RegisterRequest;
import com.careerpath.careerpathai.entity.Role;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.UserAlreadyExistsException;
import com.careerpath.careerpathai.repository.RoleRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void loginShouldReturnAuthResponseWhenCredentialsAreValid() {
        LoginRequest request = new LoginRequest();
        request.setEmail("student@example.com");
        request.setPassword("secret123");

        User user = new User();
        user.setId(7);
        user.setEmail("student@example.com");
        Role role = new Role();
        role.setName("STUDENT");
        user.setRole(role);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtil.generateToken("student@example.com", "STUDENT", 7)).thenReturn("token-123");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("token-123", response.getToken());
        assertEquals("student@example.com", response.getEmail());
        assertEquals("STUDENT", response.getRole());
        verify(jwtUtil).generateToken("student@example.com", "STUDENT", 7);
    }
        @Test
    void registerShouldThrowUserAlreadyExistsWhenEmailIsTaken() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("Jane");
        request.setLastname("Doe");
        request.setEmail("student@example.com");
        request.setPassword("secret123");

        when(userRepository.existsByEmail("student@example.com")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(request));

        verify(userRepository, never()).save(any(User.class));
        verify(roleRepository, never()).findAll();
    }
}
