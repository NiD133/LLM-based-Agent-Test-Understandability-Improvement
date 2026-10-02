package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> {
            Minutes oneAboveMinimum = Minutes.of(Integer.MIN_VALUE + 1);
            Minutes negativeTwoMinutes = Minutes.of(-2);

            oneAboveMinimum.plus(negativeTwoMinutes);
        });
    }
}
