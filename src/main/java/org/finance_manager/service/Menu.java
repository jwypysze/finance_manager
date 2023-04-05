package org.finance_manager.service;

import java.util.Scanner;

public class Menu {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void play() {
        while(true) {
            showMainMenu();
            int selectedOption = SCANNER.nextInt();
            switch (selectedOption) {
                case 0 -> System.exit(0);
                case 1 -> {

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
    }
}
