package org.finance_manager.service;

import jakarta.persistence.NoResultException;
import org.finance_manager.dto.SimpleCategoryDto;
import org.finance_manager.dto.SimpleExpenseDto;
import org.finance_manager.dto.SimpleIncomeDto;
import org.finance_manager.entity.Category;
import org.finance_manager.repository.CategoryRepository;
import org.finance_manager.repository.ExpenseRepository;
import org.finance_manager.repository.IncomeRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DemoApp {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void play() {
        final CategoryRepository categoryRepository = new CategoryRepository();
        final CategoryService categoryService = new CategoryService(categoryRepository);
        final IncomeRepository incomeRepository = new IncomeRepository();
        final IncomeService incomeService = new IncomeService(incomeRepository);
        final ExpenseRepository expenseRepository = new ExpenseRepository();
        final ExpenseService expenseService = new ExpenseService(expenseRepository);

        while(true) {
            showMainMenu();
            int selectedOption = SCANNER.nextInt();
            SCANNER.nextLine();
            switch (selectedOption) {
                case 0 -> System.exit(0);
                case 1 -> {
                    System.out.println("Type expense sum");
                    Double expenseSum = SCANNER.nextDouble();
                    SCANNER.nextLine();
                    System.out.println("Do you want to type comment? Write: YES or NO");
                    String decision = SCANNER.nextLine().toUpperCase();
                    String decisionComment = null;
                    if (decision.equals("YES")) {
                        System.out.println("Type comment");
                        decisionComment = SCANNER.nextLine();
                    }
                    System.out.println("Type category name");
                    String categoryName = SCANNER.nextLine().toLowerCase();
                    try {
                        Category byCategoryName = categoryRepository.findByCategoryName(categoryName);
                        boolean containsCategory = false;
                        if (byCategoryName != null) {
                            containsCategory = true;
                        }
                        if (containsCategory) {
                            expenseService.addExpense(expenseSum, decisionComment, byCategoryName);
                        }
                    } catch (NoResultException e) {
                        System.err.println("The category doesn't exist yet. It will be added to the category table.");
                        try {
                            categoryService.addCategory(categoryName);
                        } catch (IllegalAccessException ex) {
                            System.err.println(ex.getMessage());;
                        }
                        Category category = categoryRepository.findByCategoryName(categoryName);
                        try {
                            expenseService.addExpense(expenseSum, decisionComment, category);
                        } catch (IllegalArgumentException em) {
                            System.err.println(em.getMessage());
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Type income sum");
                    Double incomeSum = SCANNER.nextDouble();
                    SCANNER.nextLine();
                    System.out.println("Do you want to type comment? Write: YES or NO");
                    String decision = SCANNER.nextLine().toUpperCase();
                    String decisionCommentNo = null;
                    if (decision.equals("YES")) {
                        System.out.println("Type comment");
                        decisionCommentNo = SCANNER.nextLine();
                    }
                    try {
                        incomeService.addIncome(incomeSum, decisionCommentNo);
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        System.out.println("Type the expense sum you want to delete");
                        Double expenseSum = SCANNER.nextDouble();
                        SCANNER.nextLine();
                        System.out.println
                                ("Type the date of the expense you want to delete.\nRequired format: YYYY-MM-DD");
                        String expenseDate = SCANNER.nextLine();
                        LocalDate expenseDateAsDate = LocalDate.parse(expenseDate);
                        expenseService.deleteExpenseByExpenseSumAndDate(expenseSum,expenseDateAsDate);
                    } catch (DateTimeParseException e) {
                        System.err.println("The provided format of date is incorrect!\nUse: YYYY-MM-DD");
                    } catch (NoResultException e) {
                        System.err.println("The expense with provided sum and date doesn't exist!");
                    }
                }
                case 4 -> {
                    try {
                        System.out.println("Type the income sum you want to delete");
                        Double incomeSum = SCANNER.nextDouble();
                        SCANNER.nextLine();
                        System.out.println
                                ("Type the date of the income you want to delete.\nRequired format: YYYY-MM-DD");
                        String incomeDate = SCANNER.nextLine();
                        LocalDate incomeDateAsDate = LocalDate.parse(incomeDate);
                        incomeService.deleteIncomeByIncomeSumAndDate(incomeSum,incomeDateAsDate);
                    } catch (DateTimeParseException e) {
                        System.err.println("The provided format of date is incorrect!\nUse: YYYY-MM-DD");
                    } catch (NoResultException e) {
                        System.err.println("The income with provided sum and date doesn't exist!");
                    }


                }
                case 6 -> {
                    List<SimpleExpenseDto> simpleExpenseDtoList = expenseService.findAll();
                    if (!simpleExpenseDtoList.isEmpty()) {
                        simpleExpenseDtoList.forEach(simpleExpenseDto ->
                                System.out.println(simpleExpenseDto.toString()));
                    } else {
                        System.err.println("The expense table is empty.");
                    }
                }
                case 10 -> {
                    List<SimpleIncomeDto> simpleIncomeDtoList = incomeService.findAll();
                    if (!simpleIncomeDtoList.isEmpty()) {
                        simpleIncomeDtoList.forEach(simpleIncomeDto ->
                                System.out.println(simpleIncomeDto.toString()));
                    } else {
                        System.err.println("The income table is empty.");
                    }
                }
                case 12 -> {
                    System.out.println("Type category name");
                    String categoryName = SCANNER.nextLine();
                    try {
                        categoryService.addCategory(categoryName);
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        System.err.println(e.getMessage());;
                    }
                }

                case 13 -> {
                    System.out.println("Provide name of category to delete:");
                    String nameOfCategoryToDelete = SCANNER.nextLine();
                    nameOfCategoryToDelete.toLowerCase();
                    try {
                        categoryService.deleteCategoryByName(nameOfCategoryToDelete);
                    } catch (NoResultException e) {
                        System.err.println("The category doesn't exist");
                    }
                }
                case 14 -> {
                    List<SimpleCategoryDto> simpleCategoryDtoList = categoryService.findAll();
                    if (!simpleCategoryDtoList.isEmpty()) {
                        simpleCategoryDtoList.forEach(simpleCategoryDto ->
                                System.out.println(simpleCategoryDto.toString()));
                    } else {
                        System.err.println("The category table is empty.");
                    }

                }
                default -> {
                    System.out.println("The provided text is wrong. Please use numbers from menu");
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
        System.out.println("14 - Show all categories");
    }
}
