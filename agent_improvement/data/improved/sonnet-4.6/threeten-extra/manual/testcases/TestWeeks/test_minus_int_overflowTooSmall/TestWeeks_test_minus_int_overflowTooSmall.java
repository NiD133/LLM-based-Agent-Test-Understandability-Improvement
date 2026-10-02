package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_int_overflowTooSmall {

    @Test
    public void test_minus_int_overflowTooSmall() {
        // Subtracting 2 from (MIN_VALUE + 1) wraps below Integer.MIN_VALUE
        int nearMinValue = Integer.MIN_VALUE + 1;
        assertThrows(ArithmeticException.class, () -> Weeks.of(nearMinValue).minus(2));
    }
}
