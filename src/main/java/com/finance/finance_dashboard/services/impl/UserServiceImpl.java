package com.finance.finance_dashboard.services.impl;

import com.finance.finance_dashboard.dto.UserRequestDto;
import com.finance.finance_dashboard.entity.Role;
import com.finance.finance_dashboard.entity.User;
import com.finance.finance_dashboard.entity.enums.Status;
import com.finance.finance_dashboard.repository.RoleRepository;
import com.finance.finance_dashboard.repository.UserRepository;
import com.finance.finance_dashboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserRequestDto user) {

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        User newUser = new User();

        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());

        // Encode password
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set status
        if (user.getStatus() != null) {
            newUser.setStatus(Status.valueOf(user.getStatus()));
        } else {
            newUser.setStatus(Status.ACTIVE);
        }

        // Set role
        if (user.getRole() != null) {
            Role role = roleRepository.findByName(user.getRole())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            newUser.setRole(role);
        } else {
            throw new RuntimeException("Role is required");
        }

        userRepository.save(newUser);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {

        User existingUser = getUserById(id);

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getStatus() != null) {
            existingUser.setStatus(user.getStatus());
        }

        // Update role
        if (user.getRole() != null && user.getRole().getId() != null) {
            Role role = roleRepository.findById(user.getRole().getId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            existingUser.setRole(role);
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}