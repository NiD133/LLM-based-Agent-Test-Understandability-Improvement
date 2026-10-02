package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#plus(int)} reports integer underflow.
 */
public class TestDays_test_plus_int_overflowTooSmall {

    /**
     * Adding -2 to the smallest-but-one {@code int} amount of days would push the
     * result below {@link Integer#MIN_VALUE}, so an {@link ArithmeticException}
     * must be thrown.
     */
    @Test
    public void test_plus_int_overflowTooSmall() {
        Days nearMinimum = Days.of(Integer.MIN_VALUE + 1);

        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(-2));
    }
}
