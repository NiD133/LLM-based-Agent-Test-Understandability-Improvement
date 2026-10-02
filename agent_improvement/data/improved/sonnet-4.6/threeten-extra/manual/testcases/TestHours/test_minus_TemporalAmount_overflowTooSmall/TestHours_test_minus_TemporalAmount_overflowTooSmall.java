package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        // Subtracting 2 from (MIN_VALUE + 1) would go below Integer.MIN_VALUE
        Hours nearMinimum = Hours.of(Integer.MIN_VALUE + 1);
        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(Hours.of(2)));
    }
}
