package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link UtcInstant#plus(Duration)} reports overflow by throwing
 * an {@link ArithmeticException} when the result would exceed the supported range.
 */
public class TestUtcInstant_test_plus_overflowTooBig {

    /** Number of seconds in a standard (non-leap) day. */
    private static final long SECS_PER_DAY = 24L * 60 * 60;

    /** Number of nanoseconds in one second. */
    private static final long NANOS_PER_SEC = 1_000_000_000L;

    /** Number of nanoseconds in a standard (non-leap) day. */
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    @Test
    public void test_plus_overflowTooBig() {
        // Start at the very last nanosecond of the largest representable day.
        UtcInstant maxInstant = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);

        // Adding even a single nanosecond pushes past the supported range.
        assertThrows(ArithmeticException.class, () -> maxInstant.plus(Duration.ofNanos(1)));
    }
}
