package org.example.model;

import org.junit.jupiter.api.BeforeAll;

import java.util.Date;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @BeforeAll
    static void beforeAll() {
        Date date = new Date();
        System.out.println("Testing class Calculator. " + date);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 2 + 2",
            "8, 5 + 3",
            "25.5, 15.4 + 10.1",
            "144, ( 2 + 4 ) * ( ( 10 - 2 ) * 3 )",
            "4, 7 + -3",
            "19, 2 + 3 * 4 + 5",
            "8.3, 2.3 + 1.5 * 4"
    })
    void testCalculateEquals(Double expected, String expression) {
        assertEquals(expected, new Calculator(expression).calculate());
    }

    @ParameterizedTest
    @CsvSource({
            "10, 1 + 1 * 5",
            "25, 2 + 3 * 4 + 5",
            "18, 4 + 8 / 2 * 3"
    })
    void testCalculateNotEquals(Double notExpected, String expression) {
        assertNotEquals(notExpected, new Calculator(expression).calculate());
    }

    @ParameterizedTest
    @CsvSource({
            "2 ++ 2",
            "15 -/+ 959",
            "( 26 + 3 ) )",
            ")())",
            "( ( 2 + 2 ) ) + ( 1 + 3 ) )",
    })
    void testConstructorFailing(String expression) {
        assertThrows(RuntimeException.class, () -> new Calculator(expression));
    }

    @ParameterizedTest
    @CsvSource({
            "2 + 2",
            "15 * 4 + 2",
            "( 2 + 2 ) / ( 4 - 1 )",
            "2 + 4 / 2 - ( 4 - 10 * 5 ) / 3",
            "( 1 + ( 10 / 2 ) - ( 5 + 4 * ( 2 + 3 ) ) )"
    })
    void testConstructorSuccessful(String expression) {
        assertDoesNotThrow(() -> new Calculator(expression));
    }
}