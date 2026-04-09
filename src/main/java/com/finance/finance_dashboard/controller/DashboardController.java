package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.*;
import com.finance.finance_dashboard.entity.User;
import com.finance.finance_dashboard.repository.UserRepository;
import com.finance.finance_dashboard.services.DashboardSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@EnableMethodSecurity
public class DashboardController {

    private final UserRepository userRepository;
    private final DashboardSummaryService dashboardSummaryService;

    public DashboardController(UserRepository userRepository,
                     DashboardSummaryService dashboardSummaryService) {
        this.userRepository = userRepository;
        this.dashboardSummaryService = dashboardSummaryService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','VIEWER','ANALYST')")
    @GetMapping("/recent-transactions")
    public ResponseEntity<List<RecentTransactionDTO>> getRecentTransactions(
            @RequestParam(required = false) Long userId,
            Authentication auth) {

        Long finalUserId = resolveUserId(auth, userId);

        return ResponseEntity.ok(
                dashboardSummaryService.getRecentTransactions(finalUserId)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','VIEWER','ANALYST')")
    @GetMapping("/monthly-trend")
    public ResponseEntity<List<TrendDTO>> getMonthlyTrend(
            @RequestParam(required = false) Long userId,
            Authentication auth) {

        Long finalUserId = resolveUserId(auth, userId);

        return ResponseEntity.ok(
                dashboardSummaryService.getMonthlyTrendWithCategories(finalUserId)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','VIEWER','ANALYST')")
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getDashboardSummary(
            @RequestParam(required = false) Long userId,
            Authentication auth) {

        Long finalUserId = resolveUserId(auth, userId);

        return ResponseEntity.ok(
                dashboardSummaryService.getDashboardSummary(finalUserId)
        );
    }


    private Long resolveUserId(Authentication auth, Long userId) {

        String email = auth.getName();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Logged-in user not found");
        }

        Long loggedInUserId = user.getId();
        String role = user.getRole().getName();

        if ("ADMIN".equalsIgnoreCase(role)  || "ANALYST".equalsIgnoreCase(role)) {
            return (userId != null) ? userId : loggedInUserId;
        }

        if (userId != null && !userId.equals(loggedInUserId)) {
            throw new AccessDeniedException(
                    "Only ADMIN can access other users' dashboard"
            );
        }

        return loggedInUserId;
    }

}
