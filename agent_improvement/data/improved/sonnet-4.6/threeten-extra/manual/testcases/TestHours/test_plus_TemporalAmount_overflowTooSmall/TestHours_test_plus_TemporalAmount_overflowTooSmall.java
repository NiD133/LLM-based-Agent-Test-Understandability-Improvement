package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        // Adding -2 to (MIN_VALUE + 1) would produce MIN_VALUE - 1, which underflows int
        Hours nearMinValue = Hours.of(Integer.MIN_VALUE + 1);
        Hours negativeTwo = Hours.of(-2);
        assertThrows(ArithmeticException.class, () -> nearMinValue.plus(negativeTwo));
    }
}
