package org.finance_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@NoArgsConstructor
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_sum")
    private Double expenseSum;

    @Column(name = "expense_date")
    @CreationTimestamp
    private LocalDate expenseDate;

    private String comment;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    public Expense(Double expenseSum, String comment, Category category) {
        this.expenseSum = expenseSum;
        this.comment = comment;
        this.category = category;
    }

    public Expense(Double expenseSum, Category category) {
        this.expenseSum = expenseSum;
        this.category = category;
    }

    public Expense(Double expenseSum, String comment) {
        this.expenseSum = expenseSum;
        this.comment = comment;
    }
}
