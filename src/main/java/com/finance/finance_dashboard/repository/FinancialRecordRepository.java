package com.finance.finance_dashboard.repository;

import com.finance.finance_dashboard.dto.*;
import com.finance.finance_dashboard.entity.FinancialRecord;
import com.finance.finance_dashboard.entity.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByUserIdAndIsDeletedFalse(Long userId);

    @Query("SELECT new com.finance.finance_dashboard.dto.FinancialFilterDTO(" +
            "t.amount, t.type, c.name, t.transactionDate, t.createdAt, t.updatedAt, t.note) " +
            "FROM FinancialRecord t " +
            "JOIN t.category c " +
            "WHERE t.type = :type AND t.user.id = :userId")
    List<FinancialFilterDTO> findByUserIdAndType(Long userId, TransactionType type);

    @Query("SELECT new com.finance.finance_dashboard.dto.FinancialFilterDTO(" +
            "t.amount, t.type, c.name, t.transactionDate, t.createdAt, t.updatedAt, t.note) " +
            "FROM FinancialRecord t " +
            "JOIN t.category c " +
            "WHERE c.name = :category AND t.user.id = :userId")
    List<FinancialFilterDTO> findByUserIdAndCategory(Long userId, String category);

    @Query("SELECT new com.finance.finance_dashboard.dto.FinancialFilterDTO(" +
            "t.amount, t.type, c.name, t.transactionDate, " +
            "t.createdAt, t.updatedAt, t.note) " +
            "FROM FinancialRecord t " +
            "JOIN t.category c " +
            "WHERE t.user.id = :userId " +
            "AND t.isDeleted = false " +
            "AND (CAST(:startDate AS date) IS NULL OR t.transactionDate >= :startDate) " +
            "AND (CAST(:endDate AS date) IS NULL OR t.transactionDate <= :endDate)")
    List<FinancialFilterDTO> findFilterByDateRecords(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT COALESCE(SUM(fr.amount), 0) FROM FinancialRecord fr " +
            "WHERE fr.user.id = :userId " +
            "AND fr.type = :type " +
            "AND fr.isDeleted = false " +
            "AND (:startDate IS NULL OR fr.transactionDate >= :startDate) " +
            "AND (:endDate IS NULL OR fr.transactionDate <= :endDate)")
    Double getTotalByType(@Param("userId") Long userId,
                          @Param("type") TransactionType type,
                          @Param("startDate") LocalDate startDate,
                          @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.finance.finance_dashboard.dto.CategorySummaryDTO(c.name, SUM(fr.amount)) " +
            "FROM FinancialRecord fr JOIN fr.category c " +
            "WHERE fr.user.id = :userId " +
            "AND fr.isDeleted = false " +
            "AND (:startDate IS NULL OR fr.transactionDate >= :startDate) " +
            "AND (:endDate IS NULL OR fr.transactionDate <= :endDate) " +
            "GROUP BY c.name " +
            "ORDER BY SUM(fr.amount) DESC")
    List<CategorySummaryDTO> getCategorySummary(@Param("userId") Long userId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

//    @Query("SELECT new com.finance.finance_dashboard.dto.CategorySummaryDTO(c.name, fr.type, SUM(fr.amount)) " +
//            "FROM FinancialRecord fr JOIN fr.category c " +
//            "WHERE fr.user.id = :userId " +
//            "AND fr.isDeleted = false " +
//            "AND fr.type = :type " +
//            "AND (:startDate IS NULL OR fr.transactionDate >= :startDate) " +
//            "AND (:endDate IS NULL OR fr.transactionDate <= :endDate) " +
//            "GROUP BY c.name, fr.type " +
//            "ORDER BY SUM(fr.amount) DESC")
//    List<CategorySummaryDTO> getCategorySummaryByType(@Param("userId") Long userId,
//                                                      @Param("type") TransactionType type,
//                                                      @Param("startDate") LocalDate startDate,
//                                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.finance.finance_dashboard.dto.TransactionDTO(fr.id, fr.amount, fr.type, c.name, fr.transactionDate, fr.note) " +
            "FROM FinancialRecord fr JOIN fr.category c " +
            "WHERE fr.user.id = :userId " +
            "AND fr.isDeleted = false " +
            "AND (:startDate IS NULL OR fr.transactionDate >= :startDate) " +
            "AND (:endDate IS NULL OR fr.transactionDate <= :endDate) " +
            "ORDER BY fr.transactionDate DESC, fr.createdAt DESC")
    Page<TransactionDTO> getRecentTransactions(@Param("userId") Long userId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate,
                                               Pageable pageable);

    @Query(value = "SELECT " +
            "TO_CHAR(DATE_TRUNC(CAST(:groupBy AS text), fr.transaction_date), " +
            "CASE WHEN :groupBy = 'week' THEN 'IYYY-\"W\"IW' ELSE 'YYYY-MM' END) AS period, " +
            "COALESCE(SUM(CASE WHEN fr.type = 'INCOME' THEN fr.amount ELSE 0 END), 0) AS income, " +
            "COALESCE(SUM(CASE WHEN fr.type = 'EXPENSE' THEN fr.amount ELSE 0 END), 0) AS expense " +
            "FROM financialRecord fr " +
            "WHERE fr.user_id = :userId " +
            "AND fr.is_deleted = false " +
            "AND (:startDate IS NULL OR fr.transaction_date >= :startDate) " +
            "AND (:endDate IS NULL OR fr.transaction_date <= :endDate) " +
            "GROUP BY DATE_TRUNC(CAST(:groupBy AS text), fr.transaction_date), " +
            "CASE WHEN :groupBy = 'week' THEN 'IYYY-\"W\"IW' ELSE 'YYYY-MM' END " +
            "ORDER BY DATE_TRUNC(CAST(:groupBy AS text), fr.transaction_date)",
            nativeQuery = true)
    List<Object[]> getTrends(@Param("userId") Long userId,
                             @Param("groupBy") String groupBy,
                             @Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(t.amount), 0) " +
            "FROM FinancialRecord t " +
            "WHERE t.type = :type AND t.user.id = :userId")
    Double getTotalByTypeAndUserId(@Param("type") TransactionType type,
                                   @Param("userId") Long userId);


    @Query("SELECT COALESCE(SUM(t.amount), 0) " +
            "FROM FinancialRecord t " +
            "WHERE t.type = :type AND t.user.id = :userId")
    Double getExpenseTotalByTypeAndUserId(@Param("type") TransactionType type,
                                   @Param("userId") Long userId);

    @Query("SELECT new com.finance.finance_dashboard.dto.CategorySummaryDTO(c.name, COALESCE(SUM(t.amount), 0.0)) " +
            "FROM FinancialRecord t " +
            "JOIN t.category c " +
            "WHERE t.type = 'EXPENSE' AND t.user.id = :userId " +
            "GROUP BY c.name")
    List<CategorySummaryDTO> getCategorySummary(@Param("userId") Long userId);

    @Query("SELECT new com.finance.finance_dashboard.dto.MonthlyCategoryDTO(" +
            "MONTH(t.transactionDate), " +
            "c.name, " +
            "SUM(t.amount)) " +
            "FROM FinancialRecord t " +
            "JOIN t.category c " +
            "WHERE t.user.id = :userId " +
            "AND t.type = 'EXPENSE' " +
            "GROUP BY MONTH(t.transactionDate), c.name " +
            "ORDER BY MONTH(t.transactionDate), c.name")
    List<MonthlyCategoryDTO> getMonthlyExpenseCategorySummary(@Param("userId") Long userId);

    @Query("SELECT t FROM FinancialRecord t " +
            "WHERE t.user.id = :userId " +
            "ORDER BY t.transactionDate DESC")
    Page<FinancialRecord> findRecentTransactions(@Param("userId") Long userId,
                                                 Pageable pageable);

    @Query("SELECT new com.finance.finance_dashboard.dto.TrendDTO(" +
            "MONTH(t.transactionDate), " +
            "SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END), " +
            "SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END)) " +
            "FROM FinancialRecord t " +
            "WHERE t.user.id = :userId " +
            "GROUP BY MONTH(t.transactionDate) " +
            "ORDER BY MONTH(t.transactionDate)")
    List<TrendDTO> getMonthlyTrends(@Param("userId") Long userId);

}
