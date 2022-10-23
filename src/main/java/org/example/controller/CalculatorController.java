package org.example.controller;

import org.example.database.ExpressionDB;
import org.example.model.Calculator;
import org.example.model.Expression;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CalculatorController {
    public static void run() {
        ExpressionDB expressionDB = new ExpressionDB();
        Calculator calculator;
        Scanner scanner = new Scanner(System.in);
        String expression = "";
        int userAnswer = 0;
        while(userAnswer != 6) {
            System.out.print("\nSelect an option:" +
                    "\n1. Calculate the result of the expression" +
                    "\n2. View calculator history" +
                    "\n3. Delete expression from calculator history" +
                    "\n4. Update expression" +
                    "\n5. Filter expressions by result" +
                    "\n6. Exit" +
                    "\nYour choice: ");
            userAnswer = scanner.nextInt();
            scanner.nextLine();
            switch (userAnswer) {
                case 1 -> {
                    // Save the expression to database
                    try {
                        System.out.print("Enter the expression (through a space): ");
                        expression = scanner.nextLine();
                        calculator = new Calculator(expression);
                        expressionDB.save(new Expression(expression, calculator.calculate()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                }
                case 2 -> {
                    // List of saved expressions
                    try {
                        System.out.println("List of expressions in database:");
                        expressionDB.getAll().stream()
                                .forEach(System.out::println);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 3 -> {
                    try {
                        // Delete an expression from database
                        System.out.println("List of expressions in database:");
                        expressionDB.getAll().stream()
                                        .forEach(System.out::println);
                        System.out.print("Enter the expression you want to delete: ");
                        String expressionToDelete = scanner.nextLine();
                        for(Expression item: expressionDB.getAll()) {
                            if(item.getExpression().equals(expressionToDelete)) {
                                expressionDB.delete(item);
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 4 -> {
                    try {
                        // Change the expression
                        System.out.println("List of expressions in database:");
                        expressionDB.getAll().stream()
                                        .forEach(System.out::println);
                        System.out.print("Enter an expression you want to change: ");
                        String expressionToChange = scanner.nextLine();
                        System.out.print("Enter a new expression (through a space): ");
                        String newExpession = scanner.nextLine();
                        calculator = new Calculator(newExpession);
                        String[] params = {expressionToChange};
                        expressionDB.update(new Expression(newExpession, calculator.calculate()), params);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 5 -> {
                    // Filter expressions which saved in database
                    System.out.print("How do you want to filter expressions?" +
                            "\n\ta) less than number" +
                            "\t\tb) equal to number" +
                            "\t\tc) more than number" +
                            "\nYour choice: ");
                    String filterChoice = scanner.nextLine();
                    System.out.print("Enter a number for filter: ");
                    int filterNumber = scanner.nextInt();
                    switch (filterChoice) {
                        // Filter results of expressions less than number entered by the user
                        case "a" -> {
                            System.out.printf("Expressions whose result \u001B[34m<%d\u001B[0m:\n", filterNumber);
                            try {
                                expressionDB.getAll().stream()
                                        .filter(item -> item.getResult() < filterNumber)
                                        .collect(Collectors.toList())
                                        .forEach(System.out::println);
                            } catch (SQLException e) {
                                throw new RuntimeException();
                            }
                            break;
                        }
                        // Filter results of expressions equals to number entered by the user
                        case "b" -> {
                            System.out.printf("Expressions whose result \u001B[34m=%d\u001B[0m:\n", filterNumber);
                            try {
                                expressionDB.getAll().stream()
                                        .filter(item -> item.getResult() == filterNumber)
                                        .collect(Collectors.toList())
                                        .forEach(System.out::println);
                            } catch (SQLException e) {
                                throw new RuntimeException();
                            }
                            break;
                        }
                        // Filter results of expressions more than number entered by the user
                        case "c" -> {
                            System.out.printf("Expressions whose result \u001B[34m>%d\u001B[0m:\n", filterNumber);
                            try {
                                expressionDB.getAll().stream()
                                        .filter(item -> item.getResult() > filterNumber)
                                        .collect(Collectors.toList())
                                        .forEach(System.out::println);
                            } catch (SQLException e) {
                                throw new RuntimeException();
                            }
                            break;
                        }
                    }
                    break;
                }
                default -> { break; }
            }
        }
        System.out.println("\u001B[31m✖ Calculator is done!\u001B[0m");
    }
}
