package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> {
            Hours almostMinimum = Hours.of(Integer.MIN_VALUE + 1);
            Hours amountToSubtract = Hours.of(2);

            almostMinimum.minus(amountToSubtract);
        });
    }
}
