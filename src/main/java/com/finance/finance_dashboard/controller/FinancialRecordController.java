package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.FinancialFilterDTO;
import com.finance.finance_dashboard.dto.FinancialRecordRequestDTO;
import com.finance.finance_dashboard.entity.FinancialRecord;
import com.finance.finance_dashboard.services.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
@EnableMethodSecurity
public class FinancialRecordController {

    private final FinancialRecordService service;

    public FinancialRecordController(FinancialRecordService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addRecord/{userId}")
    public FinancialRecord create(@Valid @RequestBody FinancialRecordRequestDTO dto,
                                  @PathVariable Long userId) {
        return service.createRecord(dto, userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/getAll/{userId}")
    public List<FinancialRecord> getAll(@PathVariable Long userId) {
        return service.getAllRecords(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateRecord/{id}")
    public FinancialRecord update(@PathVariable Long id,
                                  @Valid @RequestBody FinancialRecordRequestDTO dto) {
        return service.updateRecord(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteRecord/{financialRecordId}")
    public String delete(@PathVariable Long financialRecordId) {
        service.deleteRecord(financialRecordId);
        return "Record deleted successfully";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/permanentDeleteRecord/{financialRecordId}")
    public String permanentDelete(@PathVariable Long financialRecordId) {
        service.permanentDeleteRecord(financialRecordId);
        return "Record deleted permanently";
    }

}