package org.finance_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    private Double incomeSum;

    @Column(name = "income_date")
    @CreationTimestamp
    private LocalDate incomeDate;
    private String comment;


    public Income(Double incomeSum, String comment) {
        this.incomeSum = incomeSum;
        this.comment = comment;
    }

    public Income(Double incomeSum) {
        this.incomeSum = incomeSum;
    }
}
