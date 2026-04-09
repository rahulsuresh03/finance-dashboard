package com.finance.finance_dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyCategoryDTO {

    private Integer month;
    private String category;
    private Double totalAmount;

    public MonthlyCategoryDTO(String category,Double totalAmount){
        this.category=category;
        this.totalAmount=totalAmount;
    }

    public MonthlyCategoryDTO(Integer month,String category,Double totalAmount){
        this.category=category;
        this.month=month;
        this.totalAmount=totalAmount;
    }

}