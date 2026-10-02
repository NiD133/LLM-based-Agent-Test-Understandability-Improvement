package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link UtcInstant#withNanoOfDay(long)}.
 * <p>
 * {@code withNanoOfDay} returns a copy of the instant with the nano-of-day replaced,
 * keeping the same Modified Julian Day. The new nano-of-day is validated against that
 * day: a normal day allows {@code [0, NANOS_PER_DAY)}, while a leap-second day allows the
 * extra second up to {@code [0, NANOS_PER_LEAP_DAY)}. An out-of-range value is rejected
 * with a {@link DateTimeException}.
 */
public class TestUtcInstant_test_withNanoOfDay {

    // Modified Julian Days around the 1972-12-31 leap second (an extra second at 23:59:60).
    private static final long MJD_1972_12_30 = 41681;       // normal day
    private static final long MJD_1972_12_31_LEAP = 41682;  // leap-second day
    private static final long MJD_1973_01_01 = 41683;       // normal day

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1000000000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;
    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    /**
     * Cases for {@link #test_withNanoOfDay}.
     * <p>
     * Each row is {@code [startMjd, startNanoOfDay, newNanoOfDay, expectedMjd, expectedNanoOfDay]}.
     * A {@code null} expectedMjd means {@code withNanoOfDay(newNanoOfDay)} must throw.
     */
    public static @Nullable Object[][] data_withNanoOfDay() {
        return new @Nullable Object[][] {
            // Simple in-range replacements keep the MJD and adopt the new nano-of-day.
            {0L, 12345L, 1L, 0L, 1L},
            {0L, 12345L, -1L, null, 0L},     // negative nano-of-day is invalid
            {7L, 12345L, 2L, 7L, 2L},
            {-99L, 12345L, 3L, -99L, 3L},

            // The largest value allowed on a normal day: NANOS_PER_DAY - 1.
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_30, NANOS_PER_DAY - 1},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1973_01_01, NANOS_PER_DAY - 1},

            // NANOS_PER_DAY (the leap-second slot) is only valid on the leap day.
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_DAY, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY, MJD_1972_12_31_LEAP, NANOS_PER_DAY},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_DAY, null, 0L},

            // The largest value allowed on a leap day: NANOS_PER_LEAP_DAY - 1; invalid on normal days.
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null, 0L},

            // NANOS_PER_LEAP_DAY is past the end of every day, so it is always invalid.
            {MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L},
            {MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withNanoOfDay")
    public void test_withNanoOfDay(long startMjd, long startNanoOfDay, long newNanoOfDay,
            @Nullable Long expectedMjd, long expectedNanoOfDay) {
        UtcInstant start = UtcInstant.ofModifiedJulianDay(startMjd, startNanoOfDay);
        if (expectedMjd != null) {
            UtcInstant result = start.withNanoOfDay(newNanoOfDay);
            assertEquals(expectedMjd.longValue(), result.getModifiedJulianDay());
            assertEquals(expectedNanoOfDay, result.getNanoOfDay());
        } else {
            assertThrows(DateTimeException.class, () -> start.withNanoOfDay(newNanoOfDay));
        }
    }
}
