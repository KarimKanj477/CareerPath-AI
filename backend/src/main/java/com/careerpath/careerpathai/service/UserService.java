package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User updateUser(Integer id, User updates);
    void deleteUser(Integer id);
}
