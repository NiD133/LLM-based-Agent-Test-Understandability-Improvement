package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_dividedBy_divideByZero {

    @Test
    public void test_dividedBy_divideByZero() {
        assertThrows(ArithmeticException.class, () -> Days.of(1).dividedBy(0));
    }
}
