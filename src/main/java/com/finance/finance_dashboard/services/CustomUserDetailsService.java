package com.finance.finance_dashboard.services;

import com.finance.finance_dashboard.entity.User;
import com.finance.finance_dashboard.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomerUserDetails loadUserByUsername(String email) {

        User user = userRepository.findByEmail(email);

        if (user==null) {
            throw new UsernameNotFoundException("User does not exist with email: " + email);
        }

        return new CustomerUserDetails(user);
    }
}