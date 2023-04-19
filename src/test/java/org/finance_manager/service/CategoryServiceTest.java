package org.finance_manager.service;

import jakarta.persistence.EntityManager;
import org.finance_manager.DbConnection;
import org.finance_manager.dto.SimpleCategoryDto;
import org.finance_manager.entity.Category;
import org.finance_manager.repository.CategoryRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryServiceTest {
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final CategoryService categoryService = new CategoryService(categoryRepository);

    @BeforeEach
    public void setUp() {
        EntityManager entityManager = DbConnection.getEntityManager();
        Category category = new Category();
        category.setCategoryName("school");
        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterEach
    public void cleanAfterEach() {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Category c").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void shouldFindOneCategory() {
        List<SimpleCategoryDto> allCategories = categoryService.findAll();
        Assertions.assertEquals(1, allCategories.size());
    }

    @Test
    public void shouldFindTwoCategoriesAfterAddingTheSecondCategory() throws IllegalAccessException {
        categoryService.addCategory("fuel");
        List<SimpleCategoryDto> allCategories = categoryService.findAll();
        Assertions.assertEquals(2, allCategories.size());
    }

    @Test
    public void shouldFindOneCategoryAfterAddingAndDeletingTheSecondCategory() throws IllegalAccessException {
        categoryService.addCategory("vet");
        categoryService.deleteCategoryByName("vet");
        List<SimpleCategoryDto> allCategries = categoryService.findAll();
        Assertions.assertEquals(1, allCategries.size());
    }
}