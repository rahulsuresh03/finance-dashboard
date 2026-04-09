package com.finance.finance_dashboard.services.impl;

import com.finance.finance_dashboard.dto.FinancialFilterDTO;
import com.finance.finance_dashboard.dto.FinancialRecordRequestDTO;
import com.finance.finance_dashboard.entity.Category;
import com.finance.finance_dashboard.entity.FinancialRecord;
import com.finance.finance_dashboard.entity.User;
import com.finance.finance_dashboard.entity.enums.TransactionType;
import com.finance.finance_dashboard.repository.CategoryRepository;
import com.finance.finance_dashboard.repository.FinancialRecordRepository;
import com.finance.finance_dashboard.repository.UserRepository;
import com.finance.finance_dashboard.services.FinancialRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialRecordServiceImpl implements FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public FinancialRecordServiceImpl(FinancialRecordRepository recordRepository,
                                      CategoryRepository categoryRepository,
                                      UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FinancialRecord createRecord(FinancialRecordRequestDTO dto, Long userId) {

        String categoryName = dto.getCategory().trim().toLowerCase();

        Category category = categoryRepository.findByName(categoryName);
        if(category==null){
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            category = categoryRepository.save(newCategory);
        }


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FinancialRecord record = new FinancialRecord();
        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(category);
        record.setTransactionDate(dto.getTransactionDate());
        record.setNote(dto.getNote());
        record.setUser(user);

        return recordRepository.save(record);
    }

    @Override
    public List<FinancialRecord> getAllRecords(Long userId) {
        return recordRepository.findByUserIdAndIsDeletedFalse(userId);
    }

    @Override
    public FinancialRecord updateRecord(Long id, FinancialRecordRequestDTO dto) {

        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setTransactionDate(dto.getTransactionDate());
        record.setNote(dto.getNote());

        return recordRepository.save(record);
    }

    @Override
    public void deleteRecord(Long id) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setIsDeleted(true); // soft delete
        recordRepository.save(record);
    }
}