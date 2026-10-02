package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#plus(java.time.temporal.TemporalAmount)} reports
 * an arithmetic overflow when adding another {@code Minutes} amount drives the
 * result below {@link Integer#MIN_VALUE}.
 */
public class TestMinutes_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void plus_belowIntegerMinValue_throwsArithmeticException() {
        // Smallest representable amount: one above Integer.MIN_VALUE.
        Minutes nearMinimum = Minutes.of(Integer.MIN_VALUE + 1);

        // Adding -2 minutes would underflow past Integer.MIN_VALUE.
        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(Minutes.of(-2)));
    }
}
