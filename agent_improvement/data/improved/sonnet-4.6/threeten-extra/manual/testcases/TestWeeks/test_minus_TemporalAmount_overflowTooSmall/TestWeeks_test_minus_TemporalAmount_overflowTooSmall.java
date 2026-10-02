package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        // Subtracting 2 from (MIN_VALUE + 1) pushes the result below Integer.MIN_VALUE,
        // which must throw ArithmeticException due to exact-arithmetic overflow detection.
        Weeks nearMinimum = Weeks.of(Integer.MIN_VALUE + 1);
        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(Weeks.of(2)));
    }
}
