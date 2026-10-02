package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_int_overflowTooSmall {

    @Test
    public void test_plus_int_overflowTooSmall() {
        // Adding -2 to (MIN_VALUE + 1) would yield MIN_VALUE - 1, which underflows int range
        int nearMinimum = Integer.MIN_VALUE + 1;
        int amountThatCausesUnderflow = -2;

        assertThrows(ArithmeticException.class,
                () -> Minutes.of(nearMinimum).plus(amountThatCausesUnderflow));
    }
}
