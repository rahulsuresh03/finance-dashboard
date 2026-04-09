package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.LoginRequest;
import com.finance.finance_dashboard.dto.LoginResponse;
import com.finance.finance_dashboard.services.UserLoginRequestService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final UserLoginRequestService userLoginRequestService;

    public LoginController(UserLoginRequestService userLoginRequestService) {
        this.userLoginRequestService = userLoginRequestService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse=userLoginRequestService.authenticate(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

}
