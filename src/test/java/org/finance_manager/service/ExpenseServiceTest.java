package org.finance_manager.service;

import jakarta.persistence.EntityManager;
import org.finance_manager.DbConnection;
import org.finance_manager.dto.SimpleExpenseDto;
import org.finance_manager.entity.Category;
import org.finance_manager.entity.Expense;
import org.finance_manager.repository.CategoryRepository;
import org.finance_manager.repository.ExpenseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpenseServiceTest {
    private final ExpenseRepository expenseRepository = new ExpenseRepository();
    private final ExpenseService expenseService = new ExpenseService(expenseRepository);
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final CategoryService categoryService = new CategoryService(categoryRepository);

    private final Category category = new Category("fuel");

    @BeforeEach
    public void setUp() {
        EntityManager entityManager = DbConnection.getEntityManager();
        Expense expense = new Expense();
        expense.setExpenseSum(458D);
        expense.setComment("to much");
        expense.setCategory(category);
        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.persist(expense);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterEach
    public void cleanAfterEach() {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Expense e").executeUpdate();
        entityManager.createQuery("delete from Category c").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void shouldFindOneExpense() {
        List<SimpleExpenseDto> allExpenses = expenseService.findAll();
        Assertions.assertEquals(1, allExpenses.size());
    }

    @Test
    public void shouldFindNoExpensesAfterDeletingExpense() {
        expenseService.deleteExpenseByExpenseSumAndDate(458D, LocalDate.of(2023,04,19));
        List<SimpleExpenseDto> allExpenses = expenseService.findAll();
        Assertions.assertEquals(0, allExpenses.size());
    }

    @Test
    public void shouldFindTwoExpensesAfterAddingTheSecondExpenseWithExpenseSumMoreThanZero() {
        expenseService.addExpense(15D, "", category);
        List<SimpleExpenseDto> allExpenses = expenseService.findAll();
        Assertions.assertEquals(2, allExpenses.size());
    }

    @Test
    public void shouldFindTwoExpensesAfterAddingTheSecondExpenseWithExpenseSumLessOrEqualsThanZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.addExpense(0D, "", category);
        });

        String expectedMessage = "The expense sum cannot be 0 or less!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldFindOneExpenseByTheRangeOfDates() {
        List<SimpleExpenseDto> expensesByTheRangeOfDates =
                expenseService.findExpensesByTheRangeOfDates
                        (LocalDate.of
                                (2023, 04, 19), LocalDate.of(2023, 04, 19));
        Assertions.assertEquals(1, expensesByTheRangeOfDates.size());
    }

    @Test
    public void shouldFindOneExpenseByCategoryName() {
        List<SimpleExpenseDto> fuel = expenseService.findExpensesByCategoryName(category);
        Assertions.assertEquals(1, fuel.size());
    }

    @Test
    public void shouldFindTwoExpensesAfterAddingTheSecondExpenseAndLookigForExpensesByCategoryName() {
        expenseService.addExpense(154D, "0", category);
        List<SimpleExpenseDto> fuel = expenseService.findExpensesByCategoryName(category);
        Assertions.assertEquals(2, fuel.size());
    }
}
