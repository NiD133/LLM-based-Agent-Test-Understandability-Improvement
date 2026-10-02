package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#plus(java.time.temporal.TemporalAmount)} reports
 * numeric overflow instead of silently wrapping around.
 */
public class TestHours_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        // Adding 2 hours to (Integer.MAX_VALUE - 1) exceeds the int range, so the
        // total cannot be represented and the operation must fail.
        Hours nearMaxHours = Hours.of(Integer.MAX_VALUE - 1);
        Hours twoHours = Hours.of(2);

        assertThrows(ArithmeticException.class, () -> nearMaxHours.plus(twoHours));
    }
}
