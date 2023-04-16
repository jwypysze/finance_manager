package org.finance_manager.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.finance_manager.DbConnection;
import org.finance_manager.entity.Category;
import org.finance_manager.entity.Income;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IncomeRepository {
    public void insert(Income income) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(income);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Set<Income> findAll() {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Income> incomes = entityManager.createQuery("select a from Income a", Income.class).getResultList();
        entityManager.close();
        return new HashSet<>(incomes);
    }

    public Income findById(long selectedIncomeId) {
        EntityManager entityManager = DbConnection.getEntityManager();
        Income income = entityManager.find(Income.class, selectedIncomeId);
        entityManager.close();
        return income;
    }

    public Income findByIncomeSumAndDate(Double incomeSum, LocalDate incomeDate) throws NoResultException {
        EntityManager entityManager = DbConnection.getEntityManager();
        TypedQuery<Income> query = entityManager.createQuery
                ("FROM Income i WHERE i.incomeSum = :incomeSum AND i.incomeDate = :incomeDate", Income.class);
        query.setParameter("incomeSum", incomeSum);
        query.setParameter("incomeDate", incomeDate);
        return query.getSingleResult();
    }

    public void deleteIncomeByIncomeSumAndDate(Income income) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(income) ? income : entityManager.merge(income));
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
