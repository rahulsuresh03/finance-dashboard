package com.finance.finance_dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DashboardSummaryDTO {

    private Double totalIncome;
    private Double totalExpense;
    private Double netBalance;

    private List<CategorySummaryDTO> categorySummary;
    private List<RecentTransactionDTO> recentTransactions;
    private List<TrendDTO> monthlyTrends;

    public DashboardSummaryDTO(Double totalIncome,
                               Double totalExpense,
                               Double netBalance,
                               List<CategorySummaryDTO> categorySummary,
                               List<RecentTransactionDTO> recentTransactions,
                               List<TrendDTO> monthlyTrends) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.netBalance = netBalance;
        this.categorySummary = categorySummary;
        this.recentTransactions = recentTransactions;
        this.monthlyTrends = monthlyTrends;
    }
}
