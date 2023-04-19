package org.finance_manager.service;

import com.mysql.cj.util.StringUtils;
import jakarta.persistence.NoResultException;
import org.finance_manager.dto.SimpleCategoryDto;
import org.finance_manager.entity.Category;
import org.finance_manager.repository.CategoryRepository;

import java.util.List;
import java.util.Set;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(String categoryName) throws IllegalAccessException, IllegalArgumentException {
        List<SimpleCategoryDto> all = findAll();
        for (SimpleCategoryDto s : all) {
            String nameOfCategory = s.getCategoryName();
            if (nameOfCategory.equalsIgnoreCase(categoryName)) {
                throw new IllegalArgumentException("The provided categoryName already exists!");
            }
        }
        if ((!StringUtils.isNullOrEmpty(categoryName))) {

            Category category = new Category(categoryName.toLowerCase());

            categoryRepository.insert(category);
        } else {
            throw new IllegalAccessException("Category name cannot be null or empty");
        }
    }

    public List<SimpleCategoryDto> findAll() {
        Set<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new SimpleCategoryDto(category.getId(),
                        category.getCategoryName()))
                .toList();
    }

    public void deleteCategoryByName(String nameOfCategoryToDelete) throws NoResultException {
        Category category = categoryRepository.findByCategoryName(nameOfCategoryToDelete);
        categoryRepository.deleteCategoryByName(category);
    }
}

