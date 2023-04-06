package org.finance_manager.service;

import jakarta.persistence.NoResultException;
import org.finance_manager.dto.SimpleCategoryDto;
import org.finance_manager.repository.CategoryRepository;

import java.util.List;
import java.util.Scanner;

public class DemoApp {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void play() {
        final CategoryRepository categoryRepository = new CategoryRepository();
        final CategoryService categoryService = new CategoryService(categoryRepository);
        while(true) {
            showMainMenu();
            int selectedOption = SCANNER.nextInt();
            SCANNER.nextLine();
            switch (selectedOption) {
                case 0 -> System.exit(0);
                case 1 -> {}
                case 12 -> {
                    System.out.println("Type category name");
                    String categoryName = SCANNER.nextLine();
                    try {
                        categoryService.addCategory(categoryName);
                    } catch (IllegalAccessException e) {
                        System.err.println(e.getMessage());;
                    }
                }
                case 111 -> {
                    List<SimpleCategoryDto> simpleCategoryDtoList = categoryService.findAll();
                    simpleCategoryDtoList.forEach(simpleCategoryDto ->
                            System.out.println(simpleCategoryDto.toString()));
                }
                case 13 -> {
                    System.out.println("Provide name of category to delete:");
                    String nameOfCategoryToDelete = SCANNER.nextLine();
                    try {
                        categoryService.deleteByCategoryName(nameOfCategoryToDelete);
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    } catch (NoResultException e) {
                        System.err.println(e.getMessage());
                    }

                }
            }
        }
    }
    private static void showMainMenu() {
        System.out.println("Type operation:");
        System.out.println("0 - Exit programme");
        System.out.println("1 - Add new expense");
        System.out.println("2 - Add new income");
        System.out.println("3 - Delete expense");
        System.out.println("4 - Delete income");
        System.out.println("5 - Show all expenses and incomes");
        System.out.println("6 - Show all expenses");
        System.out.println("7 - Show expenses based on the range of dates");
        System.out.println("8 - Show all expenses based on chosen category");
        System.out.println("9 - Show sum and the number of expenses in the chosen category");
        System.out.println("10 - Show all incomes");
        System.out.println("11 - Show the balance");
        System.out.println("12 - Add new category");
        System.out.println("13 - Delete category");
        System.out.println("111 - Find All categories");
    }
}
