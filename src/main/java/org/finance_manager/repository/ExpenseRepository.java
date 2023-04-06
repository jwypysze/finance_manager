package org.finance_manager.repository;

import jakarta.persistence.EntityManager;
import org.finance_manager.DbConnection;
import org.finance_manager.entity.Category;
import org.finance_manager.entity.Expense;

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

    public Expense findById(long selectedExpenseId) {
        EntityManager entityManager = DbConnection.getEntityManager();
        Expense expense = entityManager.find(Expense.class, selectedExpenseId);
        entityManager.close();
        return expense;
    }
}
