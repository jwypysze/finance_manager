package org.finance_manager.service;

import jakarta.persistence.NoResultException;
import org.finance_manager.dto.SimpleExpenseDto;
import org.finance_manager.entity.Category;
import org.finance_manager.entity.Expense;
import org.finance_manager.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void addExpense(Double expenseSum, String comment, Category category) throws IllegalArgumentException {
        if (expenseSum > 0) {
            Expense expense = new Expense(expenseSum, comment, category);
            expenseRepository.insert(expense);
        } else {
            throw new IllegalArgumentException("The expense sum cannot be 0 or less!");
        }
    }

    public List<SimpleExpenseDto> findAll() {
        Set<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .map(expense -> new SimpleExpenseDto(expense.getId(),
                        expense.getExpenseSum(), expense.getExpenseDate(),
                        expense.getComment(), expense.getCategory().getCategoryName()))
                .toList();
    }

    public void deleteExpenseByExpenseSumAndDate(Double expenseSum, LocalDate expenseDate) throws NoResultException {
        Expense byExpenseSumAndDate = expenseRepository.findByExpenseSumAndDate(expenseSum, expenseDate);
        expenseRepository.deleteExpenseByExpenseSumAndDate(byExpenseSumAndDate);
    }


    public List<SimpleExpenseDto> findExpensesByTheRangeOfDates(LocalDate fromDate, LocalDate toDate) {
        List<Expense> expensesByTheRangeOfDates = expenseRepository.findExpensesByTheRangeOfDates(fromDate, toDate);
        return expensesByTheRangeOfDates.stream()
                    .map(e -> new SimpleExpenseDto(e.getId(), e.getExpenseSum(),
                        e.getExpenseDate(), e.getComment(), e.getCategory().getCategoryName()))
                    .toList();
    }

    public List<SimpleExpenseDto> findExpensesByCategoryName(Category category) {
        List<Expense> expensesByCategoryName = expenseRepository.findExpensesByCategoryName(category);
        return expensesByCategoryName.stream()
                .map(e -> new SimpleExpenseDto(e.getId(), e.getExpenseSum(),
                        e.getExpenseDate(), e.getComment(), e.getCategory().getCategoryName()))
                .toList();
    }
}
