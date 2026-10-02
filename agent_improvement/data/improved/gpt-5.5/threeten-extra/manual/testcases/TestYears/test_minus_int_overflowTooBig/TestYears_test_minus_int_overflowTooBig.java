package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_int_overflowTooBig {

    @Test
    public void test_minus_int_overflowTooBig() {
        Years almostMaximumYears = Years.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> almostMaximumYears.minus(-2));
    }
}
