package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.dto.UserRequestDTO;
import com.careerpath.careerpathai.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Integer id);

    UserResponseDTO createUser(UserRequestDTO requestDTO);

    UserResponseDTO updateUser(Integer id,UserRequestDTO requestDTO);

    void deleteUser(Integer id);

    UserResponseDTO getUserByEmail(String email);

}
