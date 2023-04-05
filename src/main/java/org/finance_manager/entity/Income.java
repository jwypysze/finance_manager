package org.finance_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "incomes")
@NoArgsConstructor
@Getter
@Setter
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "income_sum")
    private Long incomeSum;

    @Column(name = "income_date")
    private LocalDate incomeDate;
    private String comment;

    public Income(Long sum, LocalDate date, String comment) {
        this.incomeSum = incomeSum;
        this.incomeDate = incomeDate;
        this.comment = comment;
    }

    public Income(Long sum, LocalDate date) {
        this.incomeSum = incomeSum;
        this.incomeDate = incomeDate;
    }
}
