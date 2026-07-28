package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.UserNotFoundException;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " was not found."));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " was not found."));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Integer id, User updates) {
        User existing = getUserById(id);
        if (updates.getFirstname() != null) existing.setFirstname(updates.getFirstname());
        if (updates.getLastname() != null) existing.setLastname(updates.getLastname());
        if (updates.getExperienceLevel() != null) existing.setExperienceLevel(updates.getExperienceLevel());
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
