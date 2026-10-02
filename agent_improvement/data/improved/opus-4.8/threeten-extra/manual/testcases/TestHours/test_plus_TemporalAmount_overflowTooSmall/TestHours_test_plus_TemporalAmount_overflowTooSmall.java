package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#plus(java.time.temporal.TemporalAmount)} throws an
 * {@code ArithmeticException} when the addition underflows the {@code int} range.
 */
public class TestHours_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        // (Integer.MIN_VALUE + 1) + (-2) underflows int, so plus(...) must overflow.
        Hours nearMinimum = Hours.of(Integer.MIN_VALUE + 1);
        Hours negativeTwo = Hours.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(negativeTwo));
    }
}
