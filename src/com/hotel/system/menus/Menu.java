package com.hotel.system.menus;

import java.util.Scanner;

public abstract class Menu {
    protected Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    protected int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    protected String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public abstract boolean showMenu();

    protected double readDouble(String prompt) {

        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

}
