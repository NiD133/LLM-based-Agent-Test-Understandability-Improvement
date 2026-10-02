package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_overflowTooSmall {

    private static final int YEARS_JUST_ABOVE_MIN_VALUE = Integer.MIN_VALUE + 1;
    private static final Years NEGATIVE_TWO_YEARS = Years.of(-2);

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(
                ArithmeticException.class,
                () -> Years.of(YEARS_JUST_ABOVE_MIN_VALUE).plus(NEGATIVE_TWO_YEARS));
    }
}
