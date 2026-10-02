package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_int_overflowTooSmall {

    @Test
    public void test_minus_int_overflowTooSmall() {
        int startingWeeks = Integer.MIN_VALUE + 1;
        int weeksToSubtract = 2;

        assertThrows(ArithmeticException.class, () -> Weeks.of(startingWeeks).minus(weeksToSubtract));
    }
}
