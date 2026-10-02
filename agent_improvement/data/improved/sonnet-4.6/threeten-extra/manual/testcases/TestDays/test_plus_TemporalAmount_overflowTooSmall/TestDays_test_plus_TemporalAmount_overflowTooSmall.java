package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        // Starting just above Integer.MIN_VALUE, adding a negative amount causes underflow
        Days nearMinValue = Days.of(Integer.MIN_VALUE + 1);
        Days negativeTwo = Days.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMinValue.plus(negativeTwo));
    }
}
