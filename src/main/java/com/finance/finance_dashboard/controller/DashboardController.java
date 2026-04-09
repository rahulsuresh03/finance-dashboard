//package com.finance.finance_dashboard.controller;
//
//import com.finance.finance_dashboard.dto.*;
//import com.finance.finance_dashboard.entity.User;
//import com.finance.finance_dashboard.repository.UserRepository;
//import com.finance.finance_dashboard.services.DashboardSummaryService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class DashboardController {
//
//    private final UserRepository userRepository;
//    private final DashboardSummaryService dashboardSummaryService;
//
//    public DashboardController(UserRepository userRepository, DashboardSummaryService dashboardSummaryService) {
//        this.userRepository = userRepository;
//        this.dashboardSummaryService = dashboardSummaryService;
//    }
//
//    @GetMapping("/dashboard/recent-transactions")
//    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIWER')")
//    public ResponseEntity<List<RecentTransactionDTO>> getRecentTransactions(
//            @RequestParam(required = false) Long userId,
//            Authentication authentication) {
//
//        return ResponseEntity.ok(
//                dashboardSummaryService.getRecentTransactions(resolveUserId(authentication,userId))
//        );
//    }
//
//    @GetMapping("/dashboard/recent-transactions")
//    public ResponseEntity<List<RecentTransactionDTO>> getRecentTransactions(
//            Authentication authentication) {
//
//        return ResponseEntity.ok(
//                dashboardSummaryService.getRecentTransactions(resolveUserId(authentication))
//        );
//    }
//
//    @GetMapping("admin/dashboard/monthly-trend")
//    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
//    public ResponseEntity<List<TrendDTO>> getMonthlyTrend(@RequestParam(required = false) Long userId,Authentication authentication) {
//
//        return ResponseEntity.ok(
//                dashboardSummaryService.getMonthlyTrendWithCategories(resolveUserId(authentication,userId))
//        );
//    }
//    @GetMapping("admin/dashboard/monthly-trend")
//    public ResponseEntity<List<TrendDTO>> getMonthlyTrend(Authentication authentication) {
//
//        return ResponseEntity.ok(
//                dashboardSummaryService.getMonthlyTrendWithCategories(resolveUserId(authentication))
//        );
//    }
//
//    @GetMapping("/dashboard")
//    public ResponseEntity<DashboardSummaryDTO> getUserDashboard(Authentication auth) {
//
//        return ResponseEntity.ok(
//                dashboardSummaryService.getDashboardSummary(resolveUserId(auth))
//        );
//    }
//
//    @GetMapping("/admin/dashboard")
//    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
//    public ResponseEntity<DashboardSummaryDTO> getAdminDashboard(
//            @RequestParam Long userId,
//            Authentication auth) {
//
//        return ResponseEntity.ok(
//                dashboardSummaryService.getDashboardSummary(userId)
//        );
//    }
//
//    private Long resolveUserId(Authentication auth) {
//        String email = auth.getName();
//        User user = userRepository.findByEmail(email);
//        return user.getId();
//    }
//
//
//    private Long resolveUserId(Authentication auth, Long userId) {
//        String email = auth.getName();
//        User user = userRepository.findByEmail(email);
//        Long loggedInUserId=user.getId();
//        Long finalUserId;
//        String role = user.getRole().getName();
//        System.out.println(role);
//
//        if (role.equals("ADMIN") && userId != null) {
//            finalUserId = userId;
//        } else {
//            finalUserId = loggedInUserId;
//        }
//        return finalUserId;
//    }
//
//}
