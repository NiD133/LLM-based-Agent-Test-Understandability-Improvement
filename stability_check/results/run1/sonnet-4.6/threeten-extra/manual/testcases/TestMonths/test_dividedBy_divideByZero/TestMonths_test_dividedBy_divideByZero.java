package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_dividedBy_divideByZero {

    @Test
    public void test_dividedBy_divideByZero() {
        // Dividing any Months value by zero must throw ArithmeticException (integer division by zero)
        Months oneMonth = Months.of(1);
        assertThrows(ArithmeticException.class, () -> oneMonth.dividedBy(0));
    }
}
