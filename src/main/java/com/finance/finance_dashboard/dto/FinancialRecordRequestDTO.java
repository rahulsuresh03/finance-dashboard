package com.finance.finance_dashboard.dto;

import com.finance.finance_dashboard.entity.enums.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.security.core.parameters.P;

import java.lang.annotation.ElementType;
import java.lang.reflect.Type;
import java.time.LocalDate;

@Getter
@Setter
public class FinancialRecordRequestDTO {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Transaction date is required")
    @PastOrPresent(message = "Transaction date cannot be in future")
    private LocalDate transactionDate;

    @Size(max = 255, message = "Note cannot exceed 255 characters")
    private String note;
}