package com.finance.finance_dashboard.services.impl;

import com.finance.finance_dashboard.dto.FinancialFilterDTO;
import com.finance.finance_dashboard.entity.enums.TransactionType;
import com.finance.finance_dashboard.repository.FinancialRecordRepository;
import com.finance.finance_dashboard.services.FinancialRecordFilterService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialRecordFilterImpl implements FinancialRecordFilterService {

    private final FinancialRecordRepository recordRepository;

    public FinancialRecordFilterImpl(FinancialRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public List<FinancialFilterDTO> filterByType(Long userId, String type) {
        return recordRepository.findByUserIdAndType(
                userId, TransactionType.valueOf(type)
        );
    }

    @Override
    public List<FinancialFilterDTO> filterByCategory(Long userId, String category) {
        return recordRepository.findByUserIdAndCategory(userId, category);
    }

    @Override
    public List<FinancialFilterDTO> filterByDate(Long userId, LocalDate start, LocalDate end) {
        return recordRepository.findFilterByDateRecords(
                userId,
                start,
                end
        );
    }

}
