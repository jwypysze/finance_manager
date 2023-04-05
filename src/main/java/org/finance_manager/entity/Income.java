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

    private Long sum;
    private LocalDate date;
    private String comment;

    public Income(Long sum, LocalDate date, String comment) {
        this.sum = sum;
        this.date = date;
        this.comment = comment;
    }

    public Income(Long sum, LocalDate date) {
        this.sum = sum;
        this.date = date;
    }
}
