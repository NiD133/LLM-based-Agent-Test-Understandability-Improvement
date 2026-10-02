package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_int_overflowTooBig {

    @Test
    public void test_plus_int_overflowTooBig() {
        int yearsOneBelowMaximum = Integer.MAX_VALUE - 1;
        int yearsToAdd = 2;

        assertThrows(
                ArithmeticException.class,
                () -> Years.of(yearsOneBelowMaximum).plus(yearsToAdd));
    }
}
