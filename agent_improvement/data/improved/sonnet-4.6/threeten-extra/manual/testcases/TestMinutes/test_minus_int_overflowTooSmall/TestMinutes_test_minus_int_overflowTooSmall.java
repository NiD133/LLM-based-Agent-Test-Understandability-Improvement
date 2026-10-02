package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_int_overflowTooSmall {

    @Test
    public void test_minus_int_overflowTooSmall() {
        // Subtracting 2 from (MIN_VALUE + 1) would produce a result below Integer.MIN_VALUE
        Minutes nearMinimum = Minutes.of(Integer.MIN_VALUE + 1);
        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(2));
    }
}
