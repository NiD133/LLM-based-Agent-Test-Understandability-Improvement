package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> {
            Minutes almostMaximumMinutes = Minutes.of(Integer.MAX_VALUE - 1);
            Minutes negativeTwoMinutes = Minutes.of(-2);

            almostMaximumMinutes.minus(negativeTwoMinutes);
        });
    }
}
