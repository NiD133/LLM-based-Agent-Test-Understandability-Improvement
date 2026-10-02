package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_int_overflowTooSmall {

    @Test
    public void test_plus_int_overflowTooSmall() {
        // Start one above Integer.MIN_VALUE so that adding -2 wraps below the minimum
        Weeks nearMinimum = Weeks.of(Integer.MIN_VALUE + 1);
        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(-2));
    }
}
