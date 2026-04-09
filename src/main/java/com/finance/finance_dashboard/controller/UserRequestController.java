package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.UserRequestDto;
import com.finance.finance_dashboard.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/admin")
@EnableMethodSecurity
public class UserRequestController {
    @Autowired
    private UserServiceImpl userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserRequestDto userRequestDto){
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        userService.createUser(userRequestDto);
        return ResponseEntity.ok("Success");
    }
}
