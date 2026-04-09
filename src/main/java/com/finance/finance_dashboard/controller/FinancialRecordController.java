package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.FinancialFilterDTO;
import com.finance.finance_dashboard.dto.FinancialRecordRequestDTO;
import com.finance.finance_dashboard.entity.FinancialRecord;
import com.finance.finance_dashboard.services.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    private final FinancialRecordService service;

    public FinancialRecordController(FinancialRecordService service) {
        this.service = service;
    }

    @PostMapping("/addRecord")
    @PreAuthorize("hasRole('ADMIN')")
    public FinancialRecord create(@Valid @RequestBody FinancialRecordRequestDTO dto,
                                  @PathVariable Long userId) {
        return service.createRecord(dto, userId);
    }

    @GetMapping("/getAll/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public List<FinancialRecord> getAll(@PathVariable Long userId) {
        return service.getAllRecords(userId);
    }

    @PutMapping("/updateRecord/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FinancialRecord update(@PathVariable Long id,
                                  @Valid @RequestBody FinancialRecordRequestDTO dto) {
        return service.updateRecord(id, dto);
    }

    @DeleteMapping("/deleteRecord/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return "Record deleted successfully";
    }

}