package org.finance_manager;

import jakarta.persistence.NoResultException;
import org.finance_manager.dto.SimpleCategoryDto;
import org.finance_manager.dto.SimpleExpenseDto;
import org.finance_manager.dto.SimpleIncomeDto;
import org.finance_manager.entity.Category;
import org.finance_manager.repository.CategoryRepository;
import org.finance_manager.repository.ExpenseRepository;
import org.finance_manager.repository.IncomeRepository;
import org.finance_manager.service.CategoryService;
import org.finance_manager.service.ExpenseService;
import org.finance_manager.service.IncomeService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.finance_manager.Colour.*;

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
                case 5 -> {
                    List<SimpleExpenseDto> allExpenses = expenseService.findAll();
                    if (!allExpenses.isEmpty()) {
                        System.out.println(ANSI_RED + "\t\t\t\t\t\t\t\t\t\tEXPENSES" + ANSI_RESET);
                        System.out.printf("%10s %10s %15s %30s %15s %n", "ID", "SUM", "DATE", "COMMENT", "CATEGORY NAME");
                        allExpenses.forEach(expense ->
                                System.out.printf("%10d %10.2f %15tF %30s %15s %n",
                                        expense.getId(), expense.getExpenseSum(), expense.getExpenseDate(),
                                        expense.getComment(), expense.getCategoryName()));
                    } else {
                        System.err.println("The expense table is empty.");
                    }
                    List<SimpleIncomeDto> allIncomes = incomeService.findAll();
                    if (!allIncomes.isEmpty()) {
                        System.out.println(ANSI_GREEN + "\t\t\t\t\t\t\t\tINCOMES" + ANSI_RESET);
                        System.out.printf("%10s %10s %15s %30s %n", "ID", "SUM", "DATE", "COMMENT");
                        allIncomes.forEach(income ->
                                System.out.printf("%10d %10.2f %15tF %30s %n", income.getId(), income.getIncomeSum(),
                                        income.getIncomeDate(), income.getComment()));
                    } else {
                        System.err.println("The income table is empty.");
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
                case 7 -> {
                    System.out.println("In this option you can show expenses based on the range of dates (FROM - TO)");
                    System.out.println("Type the first date (FROM)");
                    System.out.println("Please use format YYYY-MM-DD");
                    String fromDateAsString = SCANNER.nextLine();
                    System.out.println("Type the second date (TO)");
                    System.out.println("Please use format YYYY-MM-DD");
                    String toDateAsString = SCANNER.nextLine();
                    try {
                        LocalDate fromDate = LocalDate.parse(fromDateAsString);
                        LocalDate toDate = LocalDate.parse(toDateAsString);
                        if (fromDate.isBefore(toDate) || fromDate.equals(toDate)) {
                            List<SimpleExpenseDto> expensesByTheRangeOfDates = expenseService.
                                    findExpensesByTheRangeOfDates(fromDate, toDate);
                            if (!expensesByTheRangeOfDates.isEmpty()) {
                                expensesByTheRangeOfDates.forEach(System.out::println);
                            } else {
                                System.err.println("There are no expenses in the provided period of time.");
                            }
                        } else {
                            System.err.println
                                    ("The provided dates are wrong!\n" +
                                            "The first date (FROM) should be before the second date (TO) " +
                                            "or they should be the same if you want to show expenses made on provided day!");
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("The provided format of date is incorrect!\nUse: YYYY-MM-DD");
                    }
                }
                case 8 -> {
                    System.out.println("Type the name of category of the expenses you want to show");
                    String expenseCategory = SCANNER.nextLine().toLowerCase();
                    try {
                        Category byCategoryName = categoryRepository.findByCategoryName(expenseCategory);
                        List<Category> categories = Arrays.asList(byCategoryName);
                        if (!categories.isEmpty()) {
                            List<SimpleExpenseDto> expensesByCategoryName =
                                    expenseService.findExpensesByCategoryName(byCategoryName);
                            if (!expensesByCategoryName.isEmpty()) {
                                expensesByCategoryName.forEach(System.out::println);
                            } else {
                                System.err.println("There are no expenses with the provided category's name");
                            }
                        } else {
                            System.err.println("The provided name of category doesn't exist!");
                        }
                    } catch (NoResultException e) {
                        System.err.println("The provided name of category doesn't exist!");
                    }
                }
                case 9 -> {
                    Set<Category> allCategoriesSet = categoryRepository.findAll();
                    List<Category> allCategories = new ArrayList<>(allCategoriesSet);
                    int size = allCategories.size();
                    String[] info = new String[size];
                    for (int i = 0; i < allCategories.size(); i++) {
                        List<SimpleExpenseDto> expensesByCategoryName =
                                expenseService.findExpensesByCategoryName(allCategories.get(i));
                        if (!expensesByCategoryName.isEmpty()) {
                            String categoryName = allCategories.get(i).getCategoryName();
                            Double sumOfExpensesByCategory =
                                    expenseRepository.findSumOfExpensesByCategory(allCategories.get(i));
                            String in = "Category name: " + categoryName
                                    + ", the sum od expenses: " + sumOfExpensesByCategory
                                    + ", number of expenses: " + expensesByCategoryName.size();
                            info[i] = in;
                        }
                    }
                    for (String s : info) {
                        if (s != null) {
                            System.out.println(ANSI_BLUE + s + ANSI_RESET);
                        }
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
                case 11 -> {
                    System.out.println("In this option you can show the balance in the provided range of dates (FROM - TO)");
                    System.out.println("Type the first date (FROM)");
                    System.out.println("Please use format YYYY-MM-DD");
                    String fromDateAsString = SCANNER.nextLine();
                    System.out.println("Type the second date (TO)");
                    System.out.println("Please use format YYYY-MM-DD");
                    String toDateAsString = SCANNER.nextLine();
                    try {
                        LocalDate fromDate = LocalDate.parse(fromDateAsString);
                        LocalDate toDate = LocalDate.parse(toDateAsString);
                        if (fromDate.isBefore(toDate) || fromDate.equals(toDate)) {
                            List<SimpleIncomeDto> incomesByTheRangeOfDates =
                                    incomeService.findIncomesByTheRangeOfDates(fromDate, toDate);
                            Double sumOfIncomes = 0D;
                            for (SimpleIncomeDto s : incomesByTheRangeOfDates) {
                                Double incomeSum = s.getIncomeSum();
                                sumOfIncomes += incomeSum;
                            }
                            System.out.println
                                    (ANSI_GREEN + "The sum of incomes in the provided period of time is: " + sumOfIncomes + ANSI_RESET);
                            List<SimpleExpenseDto> expensesByTheRangeOfDates =
                                    expenseService.findExpensesByTheRangeOfDates(fromDate, toDate);
                            Double sumOfExpenses = 0D;
                            for (SimpleExpenseDto s : expensesByTheRangeOfDates) {
                                Double expenseSum = s.getExpenseSum();
                                sumOfExpenses += expenseSum;
                            }
                            System.out.println
                                    (ANSI_RED + "The sum of expenses in the provided period of time is: " + sumOfExpenses + ANSI_RESET);
                            double balance = sumOfIncomes - sumOfExpenses;
                            if (balance == 0) {
                                System.out.println
                                        (ANSI_BLUE + "The balance in the provided period of time is: " + balance + ANSI_RESET);
                            } else if (balance < 0) {
                                System.out.println
                                        (ANSI_RED + "The balance in the provided period of time is: " + balance + ANSI_RESET);
                            } else {
                                System.out.println
                                        (ANSI_GREEN + "The balance in the provided period of time is: " + balance + ANSI_RESET);
                            }
                        } else {
                            System.err.println
                                    ("The provided dates are wrong!\n" +
                                            "The first date (FROM) should be before the second date (TO) " +
                                            "or they should be the same if you want to " +
                                            "show incomes and expenses made on provided day!");
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("The provided format of date is incorrect!\nUse: YYYY-MM-DD");
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
