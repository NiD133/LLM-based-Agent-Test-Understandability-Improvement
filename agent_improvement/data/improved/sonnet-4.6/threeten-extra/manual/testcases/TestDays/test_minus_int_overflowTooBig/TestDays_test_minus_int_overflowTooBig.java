package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_int_overflowTooBig {

    @Test
    public void test_minus_int_overflowTooBig() {
        // Subtracting a negative number is equivalent to addition.
        // (MAX_VALUE - 1) - (-2) = MAX_VALUE + 1, which overflows int.
        Days nearMax = Days.of(Integer.MAX_VALUE - 1);
        int subtractNegative = -2;

        assertThrows(ArithmeticException.class, () -> nearMax.minus(subtractNegative));
    }
}
