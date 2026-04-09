package com.finance.finance_dashboard.services;

import com.finance.finance_dashboard.dto.*;
import com.finance.finance_dashboard.entity.enums.TransactionType;
import com.finance.finance_dashboard.repository.FinancialRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardSummaryService {

    @Autowired
    private FinancialRecordRepository financialRecordRepository;


    public Double getTotal(Long userId) {
        return financialRecordRepository.getTotalByTypeAndUserId(
                TransactionType.INCOME, userId);

    }

    public Double getTotalExpense(Long userId) {
        return financialRecordRepository.getExpenseTotalByTypeAndUserId(
                TransactionType.EXPENSE, userId);

    }

    public Double getNetBalance(Long userId) {
        Double totalIncome=getTotal(userId);
        Double totalExpense=getTotalExpense(userId);
        return totalIncome-totalExpense;
    }

    public List<CategorySummaryDTO> getCategorySummary(Long userId) {
        return financialRecordRepository.getCategorySummary(userId);
    }

    public List<RecentTransactionDTO> getRecentTransactions(Long userId) {

        Pageable pageable = PageRequest.of(0, 5);

        return financialRecordRepository
                .findRecentTransactions(userId, pageable)
                .getContent()
                .stream()
                .map(t -> new RecentTransactionDTO(
                        t.getAmount(),
                        t.getType().name(),
                        t.getCategory().getName(),
                        t.getTransactionDate()
                ))
                .toList();
    }


    public List<TrendDTO> getMonthlyTrendWithCategories(Long userId) {

        List<TrendDTO> monthlySummary =
                financialRecordRepository.getMonthlyTrends(userId);

        List<MonthlyCategoryDTO> monthlyCategories =
                financialRecordRepository.getMonthlyExpenseCategorySummary(userId);

        Map<Integer, TrendDTO> monthMap = new LinkedHashMap<>();

        for (TrendDTO summary : monthlySummary) {
            monthMap.put(summary.getMonth(), summary);
        }

        for (MonthlyCategoryDTO categoryDto : monthlyCategories) {
            TrendDTO monthDto = monthMap.get(categoryDto.getMonth());

            if (monthDto != null) {
                monthDto.getCategories().add(
                        new MonthlyCategoryDTO(
                                categoryDto.getCategory(),
                                categoryDto.getTotalAmount()
                        )
                );
            }
        }

        return new ArrayList<>(monthMap.values());
    }

    public DashboardSummaryDTO getDashboardSummary(Long userId) {

        Double totalIncome = getTotal(userId);
        Double totalExpense = getTotalExpense(userId);
        Double netBalance = totalIncome - totalExpense;

        List<CategorySummaryDTO> categorySummary = getCategorySummary(userId);
        List<RecentTransactionDTO> recentTransactions = getRecentTransactions(userId);
        List<TrendDTO> monthlyTrends = getMonthlyTrendWithCategories(userId);

        return new DashboardSummaryDTO(
                totalIncome,
                totalExpense,
                netBalance,
                categorySummary,
                recentTransactions,
                monthlyTrends
        );
    }

}
