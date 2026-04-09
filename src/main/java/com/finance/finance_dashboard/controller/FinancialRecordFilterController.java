package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.FinancialFilterDTO;
import com.finance.finance_dashboard.services.FinancialRecordFilterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/filter")
public class FinancialRecordFilterController {

    private final FinancialRecordFilterService service;


    public FinancialRecordFilterController(FinancialRecordFilterService service) {
        this.service = service;
    }

    @GetMapping("/type/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public List<FinancialFilterDTO> filterByType(@PathVariable Long userId,
                                                 @RequestParam String type) {
        return service.filterByType(userId, type);
    }

    @GetMapping("/category/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public List<FinancialFilterDTO> filterByCategory(@PathVariable Long userId,
                                                     @RequestParam String category) {
        return service.filterByCategory(userId, category);
    }

    @GetMapping("/date/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public List<FinancialFilterDTO> filterByDate(@PathVariable Long userId,
                                                 @RequestParam(required = false) LocalDate start,
                                                 @RequestParam(required = false) LocalDate end) {
        return service.filterByDate(userId, start, end);
    }
}
