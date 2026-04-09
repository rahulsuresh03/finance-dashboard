package com.finance.finance_dashboard.services;
import com.finance.finance_dashboard.SecurityConfig.JwtService;
import com.finance.finance_dashboard.dto.LoginRequest;
import com.finance.finance_dashboard.dto.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginRequestService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public UserLoginRequestService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse authenticate(LoginRequest request) {
        // Let Spring Security handle the exception; it usually throws BadCredentialsException
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            String token = jwtService.generateToken(request.getEmail());
            return new LoginResponse(token); // Wrap token in DTO
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("User not found");
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid password");
        }
    }
}
