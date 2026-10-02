package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        Years almostMaximumYears = Years.of(Integer.MAX_VALUE - 1);
        Years yearsThatOverflowTheMaximum = Years.of(2);

        assertThrows(
                ArithmeticException.class,
                () -> almostMaximumYears.plus(yearsThatOverflowTheMaximum));
    }
}
