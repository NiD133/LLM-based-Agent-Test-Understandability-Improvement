package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestUtcInstant_test_minus {

    private static final long SECS_PER_DAY = 24L * 60 * 60;

    private static final long NANOS_PER_SEC = 1_000_000_000L;

    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    // Each row: baseMjd, baseNanos, durationSeconds, durationNanos, expectedMjd, expectedNanos
    public static Object[][] data_minus() {
        return new Object[][] {
            // --- Starting at MJD=0: subtract a positive duration (moves backward in time) ---

            // Subtract 2 full days and -5 nanos (net: backward to MJD=-2, 5 nanos)
            { 0, 0, 2 * SECS_PER_DAY, -5, -2, 5 },
            // Subtract 1 full day and -1 nano (net: backward to MJD=-1, 1 nano)
            { 0, 0, 1 * SECS_PER_DAY, -1, -1, 1 },
            // Subtract exactly 1 full day (backward to MJD=-1, 0 nanos)
            { 0, 0, 1 * SECS_PER_DAY, 0, -1, 0 },
            // Subtract 2 nanos (borrow one day: MJD=-1, NANOS_PER_DAY-2)
            { 0, 0, 0, 2, -1, NANOS_PER_DAY - 2 },
            // Subtract 1 nano (borrow one day: MJD=-1, NANOS_PER_DAY-1)
            { 0, 0, 0, 1, -1, NANOS_PER_DAY - 1 },

            // --- Starting at MJD=0: subtract zero (identity) ---
            { 0, 0, 0, 0, 0, 0 },

            // --- Starting at MJD=0: subtract a negative duration (moves forward in time) ---

            // Subtract -1 nano (adds 1 nano: MJD=0, 1)
            { 0, 0, 0, -1, 0, 1 },
            // Subtract -2 nanos (adds 2 nanos: MJD=0, 2)
            { 0, 0, 0, -2, 0, 2 },
            // Subtract -1 second (adds 1s worth of nanos: MJD=0, NANOS_PER_SEC)
            { 0, 0, -1, 0, 0, 1 * NANOS_PER_SEC },
            // Subtract -2 seconds (adds 2s worth of nanos: MJD=0, 2*NANOS_PER_SEC)
            { 0, 0, -2, 0, 0, 2 * NANOS_PER_SEC },
            // Subtract -3.333...s (adds 3s+333333333ns: MJD=0, 3*NANOS_PER_SEC+333333333)
            { 0, 0, -3, -333333333, 0, 3 * NANOS_PER_SEC + 333333333 },
            // Subtract -1 full day (adds one day: MJD=1, 0)
            { 0, 0, -1 * SECS_PER_DAY, 0, 1, 0 },
            // Subtract -(1 day + 1 nano) (MJD=1, 1)
            { 0, 0, -1 * SECS_PER_DAY, -1, 1, 1 },
            // Subtract -(2 days + 5 nanos) (MJD=2, 5)
            { 0, 0, -2 * SECS_PER_DAY, -5, 2, 5 },

            // --- Starting at MJD=1: subtract a positive duration (moves backward in time) ---

            // Subtract 2 full days and -5 nanos (MJD=-1, 5)
            { 1, 0, 2 * SECS_PER_DAY, -5, -1, 5 },
            // Subtract 1 full day and -1 nano (MJD=0, 1)
            { 1, 0, 1 * SECS_PER_DAY, -1, 0, 1 },
            // Subtract exactly 1 full day (MJD=0, 0)
            { 1, 0, 1 * SECS_PER_DAY, 0, 0, 0 },
            // Subtract 2 nanos (borrow one day: MJD=0, NANOS_PER_DAY-2)
            { 1, 0, 0, 2, 0, NANOS_PER_DAY - 2 },
            // Subtract 1 nano (borrow one day: MJD=0, NANOS_PER_DAY-1)
            { 1, 0, 0, 1, 0, NANOS_PER_DAY - 1 },

            // --- Starting at MJD=1: subtract zero (identity) ---
            { 1, 0, 0, 0, 1, 0 },

            // --- Starting at MJD=1: subtract a negative duration (moves forward in time) ---

            // Subtract -1 nano (MJD=1, 1)
            { 1, 0, 0, -1, 1, 1 },
            // Subtract -2 nanos (MJD=1, 2)
            { 1, 0, 0, -2, 1, 2 },
            // Subtract -1 second (MJD=1, NANOS_PER_SEC)
            { 1, 0, -1, 0, 1, 1 * NANOS_PER_SEC },
            // Subtract -2 seconds (MJD=1, 2*NANOS_PER_SEC)
            { 1, 0, -2, 0, 1, 2 * NANOS_PER_SEC },
            // Subtract -3.333...s (MJD=1, 3*NANOS_PER_SEC+333333333)
            { 1, 0, -3, -333333333, 1, 3 * NANOS_PER_SEC + 333333333 },
            // Subtract -1 full day (MJD=2, 0)
            { 1, 0, -1 * SECS_PER_DAY, 0, 2, 0 },
            // Subtract -(1 day + 1 nano) (MJD=2, 1)
            { 1, 0, -1 * SECS_PER_DAY, -1, 2, 1 },
            // Subtract -(2 days + 5 nanos) (MJD=3, 5)
            { 1, 0, -2 * SECS_PER_DAY, -5, 3, 5 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus(
            long baseMjd,
            long baseNanos,
            long durationSeconds,
            int durationNanos,
            long expectedMjd,
            long expectedNanos) {
        UtcInstant base = UtcInstant.ofModifiedJulianDay(baseMjd, baseNanos);
        UtcInstant result = base.minus(Duration.ofSeconds(durationSeconds, durationNanos));
        assertEquals(expectedMjd, result.getModifiedJulianDay());
        assertEquals(expectedNanos, result.getNanoOfDay());
    }
}
