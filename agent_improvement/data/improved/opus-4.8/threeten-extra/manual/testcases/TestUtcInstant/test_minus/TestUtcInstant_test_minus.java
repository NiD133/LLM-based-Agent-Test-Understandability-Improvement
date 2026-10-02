package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link UtcInstant#minus(Duration)}.
 * <p>
 * Each case starts from a {@code UtcInstant} built from a Modified Julian Day and
 * a nano-of-day, subtracts a {@code Duration}, and checks the resulting
 * Modified Julian Day and nano-of-day.
 */
public class TestUtcInstant_test_minus {

    /** Number of seconds in a standard (non-leap) day. */
    private static final long SECS_PER_DAY = 24L * 60 * 60;

    /** Number of nanoseconds in one second. */
    private static final long NANOS_PER_SEC = 1_000_000_000L;

    /** Number of nanoseconds in a standard (non-leap) day. */
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    /**
     * Cases for {@link #test_minus}.
     * <p>
     * Columns: startMjd, startNanoOfDay, secondsToSubtract, nanosToSubtract,
     * expectedMjd, expectedNanoOfDay.
     */
    public static Object[][] data_minus() {
        return new Object[][] {
            // subtracting from day 0, nano-of-day 0
            { 0, 0, 2 * SECS_PER_DAY, -5, -2, 5 },
            { 0, 0, 1 * SECS_PER_DAY, -1, -1, 1 },
            { 0, 0, 1 * SECS_PER_DAY, 0, -1, 0 },
            { 0, 0, 0, 2, -1, NANOS_PER_DAY - 2 },
            { 0, 0, 0, 1, -1, NANOS_PER_DAY - 1 },
            { 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, -1, 0, 1 },
            { 0, 0, 0, -2, 0, 2 },
            { 0, 0, -1, 0, 0, 1 * NANOS_PER_SEC },
            { 0, 0, -2, 0, 0, 2 * NANOS_PER_SEC },
            { 0, 0, -3, -333333333, 0, 3 * NANOS_PER_SEC + 333333333 },
            { 0, 0, -1 * SECS_PER_DAY, 0, 1, 0 },
            { 0, 0, -1 * SECS_PER_DAY, -1, 1, 1 },
            { 0, 0, -2 * SECS_PER_DAY, -5, 2, 5 },
            // subtracting from day 1, nano-of-day 0
            { 1, 0, 2 * SECS_PER_DAY, -5, -1, 5 },
            { 1, 0, 1 * SECS_PER_DAY, -1, 0, 1 },
            { 1, 0, 1 * SECS_PER_DAY, 0, 0, 0 },
            { 1, 0, 0, 2, 0, NANOS_PER_DAY - 2 },
            { 1, 0, 0, 1, 0, NANOS_PER_DAY - 1 },
            { 1, 0, 0, 0, 1, 0 },
            { 1, 0, 0, -1, 1, 1 },
            { 1, 0, 0, -2, 1, 2 },
            { 1, 0, -1, 0, 1, 1 * NANOS_PER_SEC },
            { 1, 0, -2, 0, 1, 2 * NANOS_PER_SEC },
            { 1, 0, -3, -333333333, 1, 3 * NANOS_PER_SEC + 333333333 },
            { 1, 0, -1 * SECS_PER_DAY, 0, 2, 0 },
            { 1, 0, -1 * SECS_PER_DAY, -1, 2, 1 },
            { 1, 0, -2 * SECS_PER_DAY, -5, 3, 5 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus(long startMjd, long startNanoOfDay, long secondsToSubtract,
            int nanosToSubtract, long expectedMjd, long expectedNanoOfDay) {
        UtcInstant start = UtcInstant.ofModifiedJulianDay(startMjd, startNanoOfDay);

        UtcInstant result = start.minus(Duration.ofSeconds(secondsToSubtract, nanosToSubtract));

        assertEquals(expectedMjd, result.getModifiedJulianDay());
        assertEquals(expectedNanoOfDay, result.getNanoOfDay());
    }
}
