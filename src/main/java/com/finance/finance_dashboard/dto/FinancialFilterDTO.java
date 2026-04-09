package com.finance.finance_dashboard.dto;

import com.finance.finance_dashboard.entity.enums.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FinancialFilterDTO {

        private Double amount;

        private TransactionType type;

        private String category;

        private LocalDate transactionDate;

        private LocalDateTime createdAt;

        private  LocalDateTime updatedAt;

        private String note;

    public FinancialFilterDTO(Double amount, TransactionType type, String category, LocalDate transactionDate, LocalDateTime createdAt, LocalDateTime updatedAt, String note) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.transactionDate = transactionDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.note = note;
    }
}
