package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_int_overflowTooSmall {

    @Test
    public void test_plus_int_overflowTooSmall() {
        // Adding -2 to (MIN_VALUE + 1) would produce MIN_VALUE - 1, which underflows int
        int nearMinValue = Integer.MIN_VALUE + 1;
        int amountThatCausesUnderflow = -2;

        assertThrows(ArithmeticException.class, () ->
                Years.of(nearMinValue).plus(amountThatCausesUnderflow));
    }
}
