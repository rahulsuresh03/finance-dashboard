package com.finance.finance_dashboard.services;

import com.finance.finance_dashboard.dto.FinancialFilterDTO;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordFilterService {
    List<FinancialFilterDTO> filterByType(Long userId, String type);

    List<FinancialFilterDTO> filterByCategory(Long userId, String category);

    List<FinancialFilterDTO> filterByDate(Long userId, LocalDate start, LocalDate end);
}
