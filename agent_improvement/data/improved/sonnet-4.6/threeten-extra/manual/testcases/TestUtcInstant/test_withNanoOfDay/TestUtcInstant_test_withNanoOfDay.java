package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestUtcInstant_test_withNanoOfDay {

    // MJD values: 1972-12-30 is the day before the 1972 leap-second day,
    // 1972-12-31 is the leap-second day itself, and 1973-01-01 is the day after.
    private static final long MJD_1972_12_30      = 41681;
    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01      = 41683;

    private static final long SECS_PER_DAY       = 24L * 60 * 60;
    private static final long NANOS_PER_SEC       = 1_000_000_000L;
    private static final long NANOS_PER_DAY       = SECS_PER_DAY * NANOS_PER_SEC;
    private static final long NANOS_PER_LEAP_DAY  = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    /**
     * Parameters for {@link #test_withNanoOfDay}.
     * Columns: startMjd, startNano, newNanoOfDay, expectedMjd (null = expect DateTimeException), expectedNano
     */
    @Nullable
    public static Object[][] data_withNanoOfDay() {
        return new @Nullable Object[][] {
            // --- Basic replacements on arbitrary MJD days ---
            {  0L,   12345L,  1L,   0L,   1L },   // replace nano on MJD 0, result keeps same MJD
            {  0L,   12345L, -1L,  null,  0L },   // negative nano-of-day is always invalid
            {  7L,   12345L,  2L,   7L,   2L },   // positive MJD, small valid nano
            { -99L,  12345L,  3L, -99L,   3L },   // negative MJD, small valid nano

            // --- newNanoOfDay = NANOS_PER_DAY - 1: last nanosecond of a normal day; valid on all day types ---
            { MJD_1972_12_30,      NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_30,      NANOS_PER_DAY - 1 },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1 },
            { MJD_1973_01_01,      NANOS_PER_DAY - 1, NANOS_PER_DAY - 1, MJD_1973_01_01,      NANOS_PER_DAY - 1 },

            // --- newNanoOfDay = NANOS_PER_DAY: first nanosecond of the leap second; valid only on leap day ---
            { MJD_1972_12_30,      NANOS_PER_DAY - 1, NANOS_PER_DAY, null,                0L             },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY, MJD_1972_12_31_LEAP, NANOS_PER_DAY },
            { MJD_1973_01_01,      NANOS_PER_DAY - 1, NANOS_PER_DAY, null,                0L             },

            // --- newNanoOfDay = NANOS_PER_LEAP_DAY - 1: last nanosecond of a leap day; valid only on leap day ---
            { MJD_1972_12_30,      NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null,                0L                    },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1 },
            { MJD_1973_01_01,      NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1, null,                0L                    },

            // --- newNanoOfDay = NANOS_PER_LEAP_DAY: one past the maximum; always invalid ---
            { MJD_1972_12_30,      NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L },
            { MJD_1973_01_01,      NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY, null, 0L },
        };
    }

    @ParameterizedTest
    @MethodSource("data_withNanoOfDay")
    public void test_withNanoOfDay(long mjd, long nanos, long newNanoOfDay, @Nullable Long expectedMjd, long expectedNanos) {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(mjd, nanos);
        if (expectedMjd != null) {
            UtcInstant result = instant.withNanoOfDay(newNanoOfDay);
            assertEquals(expectedMjd.longValue(), result.getModifiedJulianDay());
            assertEquals(expectedNanos, result.getNanoOfDay());
        } else {
            assertThrows(DateTimeException.class, () -> instant.withNanoOfDay(newNanoOfDay));
        }
    }
}
