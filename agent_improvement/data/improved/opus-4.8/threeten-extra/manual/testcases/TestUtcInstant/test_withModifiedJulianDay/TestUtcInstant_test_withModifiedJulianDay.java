package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link UtcInstant#withModifiedJulianDay(long)}.
 */
public class TestUtcInstant_test_withModifiedJulianDay {

    // Modified Julian Days around the 1972-12-31 leap-second day.
    private static final long MJD_1972_12_30 = 41681;
    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01 = 41683;
    private static final long MJD_1973_12_31_LEAP = MJD_1972_12_31_LEAP + 365;

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    // Nanoseconds in a normal (non-leap) day.
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    /**
     * Cases for {@link #test_withModifiedJulianDay}.
     * <p>
     * Columns: starting MJD, starting nano-of-day, the new MJD to apply, the
     * expected resulting MJD (or {@code null} when the change must be rejected),
     * and the expected resulting nano-of-day.
     */
    public static @Nullable Object[][] data_withModifiedJulianDay() {
        return new @Nullable Object[][] {
            // Changing only the MJD leaves a small nano-of-day untouched.
            {0L, 12345L, 1L, 1L, 12345L},
            {0L, 12345L, -1L, -1L, 12345L},
            {7L, 12345L, 2L, 2L, 12345L},
            {7L, 12345L, -2L, -2L, 12345L},
            {-99L, 12345L, 3L, 3L, 12345L},
            {-99L, 12345L, -3L, -3L, 12345L},
            // Start on a leap day with a nano-of-day equal to a full normal day
            // (the leap-second instant). Moving to a normal day is invalid
            // because that nano-of-day is out of range there.
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_30, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_31_LEAP, MJD_1972_12_31_LEAP, NANOS_PER_DAY},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_01_01, null, 0L},
            // Another leap day accepts the leap-second nano-of-day.
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_12_31_LEAP, MJD_1973_12_31_LEAP, NANOS_PER_DAY},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withModifiedJulianDay")
    public void test_withModifiedJulianDay(
            long mjd, long nanos, long newMjd, @Nullable Long expectedMjd, long expectedNanos) {
        UtcInstant initial = UtcInstant.ofModifiedJulianDay(mjd, nanos);
        if (expectedMjd != null) {
            UtcInstant result = initial.withModifiedJulianDay(newMjd);
            assertEquals(expectedMjd.longValue(), result.getModifiedJulianDay());
            assertEquals(expectedNanos, result.getNanoOfDay());
        } else {
            assertThrows(DateTimeException.class, () -> initial.withModifiedJulianDay(newMjd));
        }
    }
}
