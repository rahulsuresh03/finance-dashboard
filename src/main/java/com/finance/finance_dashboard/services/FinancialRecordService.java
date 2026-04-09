package com.finance.finance_dashboard.services;

import com.finance.finance_dashboard.dto.FinancialFilterDTO;
import com.finance.finance_dashboard.dto.FinancialRecordRequestDTO;
import com.finance.finance_dashboard.entity.FinancialRecord;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordService {

    FinancialRecord createRecord(FinancialRecordRequestDTO dto, Long userId);

    List<FinancialRecord> getAllRecords(Long userId);

    FinancialRecord updateRecord(Long id, FinancialRecordRequestDTO dto);

    void deleteRecord(Long id);

    void permanentDeleteRecord(Long id);
}
