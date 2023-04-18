package org.finance_manager.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.finance_manager.DbConnection;
import org.finance_manager.entity.Category;
import org.finance_manager.entity.Expense;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpenseRepository {
    public void insert(Expense expense) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(expense);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Set<Expense> findAll() {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Expense> expenses = entityManager.createQuery
                ("select a from Expense a", Expense.class).getResultList();
        entityManager.close();
        return new HashSet<>(expenses);
    }

    public Expense findByExpenseSumAndDate(Double expenseSum, LocalDate expenseDate) throws NoResultException {
        EntityManager entityManager = DbConnection.getEntityManager();
        TypedQuery<Expense> query = entityManager.createQuery
                ("FROM Expense e WHERE e.expenseSum = :expenseSum AND e.expenseDate = :expenseDate", Expense.class);
        query.setParameter("expenseSum", expenseSum);
        query.setParameter("expenseDate", expenseDate);
        return query.getSingleResult();
    }

    public void deleteExpenseByExpenseSumAndDate(Expense expense) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(expense) ? expense : entityManager.merge(expense));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Expense> findExpensesByTheRangeOfDates(LocalDate fromDate, LocalDate toDate) {
        EntityManager entityManager = DbConnection.getEntityManager();
        TypedQuery<Expense> query = entityManager.createQuery
                ("FROM Expense e WHERE e.expenseDate BETWEEN :fromDate AND :toDate", Expense.class);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        return query.getResultList();
    }

    public List<Expense> findExpensesByCategoryName(Category category) {
        EntityManager entityManager = DbConnection.getEntityManager();
        TypedQuery<Expense> query = entityManager.createQuery
                ("FROM Expense e WHERE e.category = :category", Expense.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public Double findSumOfExpensesByCategory(Category category) {
        EntityManager entityManager = DbConnection.getEntityManager();
        TypedQuery<Double> query = entityManager.createQuery
                ("SELECT SUM(e.expenseSum) FROM Expense e WHERE e.category = :category", Double.class);
        query.setParameter("category", category);
        return query.getSingleResult();
    }
}
