package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_negated_overflow {

    @Test
    public void test_negated_overflow() {
        // Integer.MIN_VALUE cannot be negated within int range (two's complement overflow)
        Months minValue = Months.of(Integer.MIN_VALUE);
        assertThrows(ArithmeticException.class, () -> minValue.negated());
    }
}
