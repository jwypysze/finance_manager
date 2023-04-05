package org.finance_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long expenseSum;

    @Column(name = "expense_date")
    private LocalDate expenseDate;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
