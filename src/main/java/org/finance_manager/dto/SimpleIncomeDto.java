package org.finance_manager.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SimpleIncomeDto {
    private long id;
    private Double incomeSum;
    private LocalDate incomeDate;
    private String comment;
}
