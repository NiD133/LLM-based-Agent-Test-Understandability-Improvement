package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Weeks#multipliedBy(int)} fails fast when the result would
 * underflow below {@link Integer#MIN_VALUE}.
 */
public class TestWeeks_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // Choose a starting amount just past half of Integer.MIN_VALUE so that
        // doubling it cannot be represented as an int.
        Weeks tooSmallToDouble = Weeks.of(Integer.MIN_VALUE / 2 - 1);

        // Multiplying by 2 overflows the int range and must throw.
        assertThrows(ArithmeticException.class, () -> tooSmallToDouble.multipliedBy(2));
    }
}
