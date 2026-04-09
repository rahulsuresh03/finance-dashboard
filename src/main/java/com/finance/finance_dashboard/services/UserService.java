package com.finance.finance_dashboard.services;

import com.finance.finance_dashboard.dto.UserRequestDto;
import com.finance.finance_dashboard.entity.User;

import java.util.List;

public interface UserService {

    void createUser(UserRequestDto user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}