package org.finance_manager;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.metamodel.Metamodel;

import java.util.Map;

public class DbConnection {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("finance_manager");
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    public static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }
}
