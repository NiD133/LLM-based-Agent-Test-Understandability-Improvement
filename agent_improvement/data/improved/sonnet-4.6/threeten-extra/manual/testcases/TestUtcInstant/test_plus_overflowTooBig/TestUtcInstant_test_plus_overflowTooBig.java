package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#plus(Duration)} throws {@link ArithmeticException}
 * when the addition would overflow beyond the maximum representable instant.
 */
public class TestUtcInstant_test_plus_overflowTooBig {

    // A standard (non-leap) day has exactly 86,400 seconds = 86,400,000,000,000 nanoseconds
    private static final long NANOS_PER_DAY = 24L * 60 * 60 * 1_000_000_000L;

    @Test
    public void test_plus_overflowTooBig() {
        // Place the instant at the very last nanosecond of the maximum MJD day;
        // adding even one nanosecond must overflow and throw ArithmeticException.
        UtcInstant maxInstant = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);

        assertThrows(ArithmeticException.class, () -> maxInstant.plus(Duration.ofNanos(1)));
    }
}
