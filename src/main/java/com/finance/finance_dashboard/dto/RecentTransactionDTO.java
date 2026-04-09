package com.finance.finance_dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RecentTransactionDTO {
    private Double amount;
    private String type;
    private String category;
    private LocalDate transactionDate;

    public RecentTransactionDTO(Double amount, String type, String category, LocalDate transactionDate) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.transactionDate = transactionDate;
    }
}
