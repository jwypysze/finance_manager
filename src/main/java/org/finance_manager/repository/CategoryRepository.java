package org.finance_manager.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.finance_manager.DbConnection;
import org.finance_manager.entity.Category;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryRepository {
    public void insert(Category category) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Set<Category> findAll() {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Category> categories = entityManager.createQuery
                ("select a from Category a", Category.class).getResultList();
        entityManager.close();
        return new HashSet<>(categories);
    }

    public Category findByCategoryName(String categoryName) throws NoResultException {
        EntityManager entityManager = DbConnection.getEntityManager();
        TypedQuery<Category> query = entityManager.createQuery
                    ("FROM Category c WHERE c.categoryName = :categoryName", Category.class);
        query.setParameter("categoryName", categoryName);
        return query.getSingleResult();
    }

    public void deleteCategoryByName(Category category) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(category) ? category : entityManager.merge(category));
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
