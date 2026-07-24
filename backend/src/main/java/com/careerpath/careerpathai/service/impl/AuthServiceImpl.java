package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.dto.RegisterRequestDTO;
import com.careerpath.careerpathai.dto.UserResponseDTO;
import com.careerpath.careerpathai.entity.Role;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.RoleNotFoundException;
import com.careerpath.careerpathai.exception.UserAlreadyExistsException;
import com.careerpath.careerpathai.repository.RoleRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO register(RegisterRequestDTO requestDTO) {

        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + requestDTO.getEmail() + " already exists.");
        }

        Role userRole = roleRepository.findByName("User")
                .orElseThrow(() -> new RoleNotFoundException("Default User role was not found.")
                );
        User user = new User();
        user.setFirstname(requestDTO.getFirstname());
        user.setLastname(requestDTO.getLastname());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(
                passwordEncoder.encode(requestDTO.getPassword())
        );
        user.setExperienceLevel(requestDTO.getExperienceLevel());
        user.setRole(userRole);

        User savedUser = userRepository.save(user);

        return mapToResponseDTO(savedUser);
    }

    private UserResponseDTO mapToResponseDTO(User user) {

        return new UserResponseDTO(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail(),
                user.getExperienceLevel(), user.getCreatedAt(), user.getRole().getId(), user.getRole().getName());
    }
}