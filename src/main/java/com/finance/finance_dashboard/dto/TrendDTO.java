package com.finance.finance_dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TrendDTO {

    private Integer month;
    private Double totalIncome;
    private Double totalExpense;
    private List<MonthlyCategoryDTO> categories = new ArrayList<>();

    public TrendDTO(Integer month, Double totalIncome, Double totalExpense) {
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }
}