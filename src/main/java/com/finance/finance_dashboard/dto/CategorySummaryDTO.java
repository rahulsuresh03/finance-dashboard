package com.finance.finance_dashboard.dto;

import com.finance.finance_dashboard.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategorySummaryDTO {

    private String category;
    private Double amount;

    public CategorySummaryDTO(String category, Double amount) {
        this.category = category;
        this.amount = amount;
    }
}
