package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.dto.UserRequestDTO;
import com.careerpath.careerpathai.dto.UserResponseDTO;
import com.careerpath.careerpathai.entity.Role;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.RoleNotFoundException;
import com.careerpath.careerpathai.exception.UserAlreadyExistsException;
import com.careerpath.careerpathai.exception.UserNotFoundException;
import com.careerpath.careerpathai.repository.RoleRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Role getRoleEntityById(Integer roleId) {

        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with id " + roleId + " was not found."));
    }

    private User getUserEntityById(Integer id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " was not found."));
    }

    private User mapToEntity(UserRequestDTO requestDTO) {

        Role role = getRoleEntityById(requestDTO.getRoleId());

        User user = new User();

        user.setFirstname(requestDTO.getFirstname());
        user.setLastname(requestDTO.getLastname());
        user.setEmail(requestDTO.getEmail());
        user.setExperienceLevel(requestDTO.getExperienceLevel());
        user.setRole(role);

        return user;
    }

    private UserResponseDTO mapToResponseDTO(User user) {

        return new UserResponseDTO(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getExperienceLevel(),
                user.getCreatedAt(), user.getRole().getId(), user.getRole().getName());
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        List<UserResponseDTO> response = new ArrayList<>();

        for (User user : users) {
            response.add(mapToResponseDTO(user));
        }

        return response;
    }

    @Override
    public UserResponseDTO getUserById(Integer id) {

        User user = getUserEntityById(id);

        return mapToResponseDTO(user);
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {

            throw new UserAlreadyExistsException("User with email " + requestDTO.getEmail() + " already exists.");
        }

        User user = mapToEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        User savedUser = userRepository.save(user);

        return mapToResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO updateUser(Integer id, UserRequestDTO requestDTO) {

        User existingUser = getUserEntityById(id);

        if (!existingUser.getEmail().equalsIgnoreCase(requestDTO.getEmail())
                && userRepository.existsByEmail(requestDTO.getEmail())) {

            throw new UserAlreadyExistsException("User with email " + requestDTO.getEmail() + " already exists.");
        }

        Role role = getRoleEntityById(requestDTO.getRoleId());

        existingUser.setFirstname(requestDTO.getFirstname());
        existingUser.setLastname(requestDTO.getLastname());
        existingUser.setEmail(requestDTO.getEmail());
        existingUser.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        existingUser.setExperienceLevel(requestDTO.getExperienceLevel());
        existingUser.setRole(role);

        User updatedUser = userRepository.save(existingUser);

        return mapToResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Integer id) {
        User user= getUserEntityById(id);
        userRepository.delete(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user= userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("user with email" +email + "was not found"));
        return mapToResponseDTO(user);
    }

}