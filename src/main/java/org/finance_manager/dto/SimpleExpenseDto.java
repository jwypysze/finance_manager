package org.finance_manager.dto;

import lombok.*;
import org.finance_manager.entity.Category;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SimpleExpenseDto {
    private Long id;
    private Double expenseSum;
    private LocalDate expenseDate;
    private String comment;
    private Category category;
    private String categoryName;

    public SimpleExpenseDto(Long id, Double expenseSum, LocalDate expenseDate, String comment, String categoryName) {
        this.id = id;
        this.expenseSum = expenseSum;
        this.expenseDate = expenseDate;
        this.comment = comment;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "SimpleExpenseDto" +
                "(id = " + id + ", expenseSum = " + expenseSum
                + ", expenseDate = " + expenseDate + ", comment = " + comment + ", categoryName = " + categoryName + ")";
    }
}
