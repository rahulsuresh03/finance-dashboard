package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.*;
import com.finance.finance_dashboard.entity.User;
import com.finance.finance_dashboard.repository.UserRepository;
import com.finance.finance_dashboard.services.DashboardSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class Dashboard {

    private final UserRepository userRepository;
    private final DashboardSummaryService dashboardSummaryService;

    public Dashboard(UserRepository userRepository,
                               DashboardSummaryService dashboardSummaryService) {
        this.userRepository = userRepository;
        this.dashboardSummaryService = dashboardSummaryService;
    }

    @GetMapping("/recent-transactions")
    public ResponseEntity<List<RecentTransactionDTO>> getRecentTransactions(
            @RequestParam(required = false) Long userId,
            Authentication auth) {

        Long finalUserId = resolveUserId(auth, userId);

        return ResponseEntity.ok(
                dashboardSummaryService.getRecentTransactions(finalUserId)
        );
    }


    @GetMapping("/monthly-trend")
    public ResponseEntity<List<TrendDTO>> getMonthlyTrend(
            @RequestParam(required = false) Long userId,
            Authentication auth) {

        Long finalUserId = resolveUserId(auth, userId);

        return ResponseEntity.ok(
                dashboardSummaryService.getMonthlyTrendWithCategories(finalUserId)
        );
    }


    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getAdminDashboard(
            @RequestParam Long userId) {

        return ResponseEntity.ok(
                dashboardSummaryService.getDashboardSummary(userId)
        );
    }

    private Long resolveUserId(Authentication auth, Long userId) {

        String email = auth.getName();
        User user = userRepository.findByEmail(email);

        Long loggedInUserId = user.getId();
        String role = user.getRole().getName();


        // ADMIN → can access anyone
        if ("ADMIN".equals(role)) {
            return (userId != null) ? userId : loggedInUserId;
        }

        // NON-ADMIN → cannot access others
        if (userId != null && !userId.equals(loggedInUserId) && !role.equals("ADMIN")) {
            throw new AccessDeniedException(
                    "You are not permitted to access other users' records"
            );
        }

        return loggedInUserId;
    }
}