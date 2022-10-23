package org.example.model;

public class Calculator {
    private String[] expressionArray;
    private int position = 0;

    public Calculator(String expression) {
        expression = expression.trim();
        expressionArray = expression.split(" ");
        // Checking expression's validation
        if (!isValid(expression)) throw new IllegalArgumentException("Invalid expression!");
        // Checking if user entered more than one operator in a row
        for (int i = 0; i < expressionArray.length; i++) {
            if (expressionArray[i].matches("^[\\+\\-\\*\\/][\\+\\-\\*\\/]+")) {
                throw new IllegalArgumentException("Used more than 1 operator in a row!");
            }
        }
        // Checking for an odd number of brackets
        if (numberOfBrackets() % 2 == 1) throw new IllegalArgumentException("Brackets missing!");
    }

    // Calculate the expression
    public double calculate() {
        double result = calculateLowPriorityOperation();
        return result;
    }

    // Calculate plus or minus operation
    public double calculateLowPriorityOperation() {
        double firstNumber = calculateHighPriorityOperation();
        while (position < expressionArray.length) {
            String operator = expressionArray[position];
            if (!operator.equals("+") && !operator.equals("-")) {
                break;
            } else {
                position++;
            }
            double secondNumber = calculateHighPriorityOperation();
            if (operator.equals("+")) {
                firstNumber += secondNumber;
            } else {
                firstNumber -= secondNumber;
            }
        }
        return firstNumber;
    }

    // Calculate multiply or divide operation
    public double calculateHighPriorityOperation() {
        double firstNumber = calculateInBrackets();
        while (position < expressionArray.length) {
            String operator = expressionArray[position];
            if (!operator.equals("*") && !operator.equals("/")) {
                break;
            } else {
                position++;
            }
            double secondNumber = calculateInBrackets();
            if (operator.equals("*")) {
                firstNumber *= secondNumber;
            } else {
                firstNumber /= secondNumber;
            }
        }
        return firstNumber;
    }

    // Calculate expressions in brackets, main priority
    public double calculateInBrackets() {
        String currentSymbol = expressionArray[position];
        double result;
        if (currentSymbol.equals("(")) {
            position++;
            result = calculate();
            String clothingBracket;
            if (position < expressionArray.length) {
                clothingBracket = expressionArray[position];
            } else {
                throw new IllegalArgumentException("Clothing bracket was not found");
            }
            if (clothingBracket.equals(")")) {
                position++;
                return result;
            }
            throw new IllegalArgumentException("Expected: \')\'. Provided by: " + clothingBracket);
        }
        position++;
        return Double.parseDouble(currentSymbol);
    }

    // Calculate number of brackets in the expression
    public int numberOfBrackets() {
        int bracketsCount = 0;
        for (int i = 0; i < expressionArray.length; i++) {
            if (expressionArray[i].equals("(") || expressionArray[i].equals(")")) {
                bracketsCount++;
            }
        }
        return bracketsCount;
    }

    // Checking expression's validation
    public boolean isValid(String expression) {
        return expression.matches("^[\\(0-9][0-9|.|\\+\\-\\*\\/\\(\\) ]+[0-9\\)]$");
    }
}
