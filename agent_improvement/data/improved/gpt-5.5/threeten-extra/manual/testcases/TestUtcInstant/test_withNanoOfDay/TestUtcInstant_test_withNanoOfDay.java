package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestUtcInstant_test_withNanoOfDay {

    private static final long MJD_1972_12_30 = 41681;
    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01 = 41683;

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;
    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    @Nullable
    public static Object[][] data_withNanoOfDay() {
        return new @Nullable Object[][] {
                validCase(0L, 12345L, 1L, 0L, 1L),
                invalidCase(0L, 12345L, -1L),
                validCase(7L, 12345L, 2L, 7L, 2L),
                validCase(-99L, 12345L, 3L, -99L, 3L),

                validCase(MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1,
                        MJD_1972_12_30, NANOS_PER_DAY - 1),
                validCase(MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1,
                        MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1),
                validCase(MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_DAY - 1,
                        MJD_1973_01_01, NANOS_PER_DAY - 1),

                invalidCase(MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_DAY),
                validCase(MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_DAY,
                        MJD_1972_12_31_LEAP, NANOS_PER_DAY),
                invalidCase(MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_DAY),

                invalidCase(MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1),
                validCase(MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1,
                        MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1),
                invalidCase(MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY - 1),

                invalidCase(MJD_1972_12_30, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY),
                invalidCase(MJD_1972_12_31_LEAP, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY),
                invalidCase(MJD_1973_01_01, NANOS_PER_DAY - 1, NANOS_PER_LEAP_DAY)
        };
    }

    private static Object[] validCase(
            long mjd,
            long nanos,
            long newNanoOfDay,
            long expectedMjd,
            long expectedNanos) {
        return new Object[] {mjd, nanos, newNanoOfDay, expectedMjd, expectedNanos};
    }

    private static Object[] invalidCase(long mjd, long nanos, long newNanoOfDay) {
        return new Object[] {mjd, nanos, newNanoOfDay, null, 0L};
    }

    @ParameterizedTest
    @MethodSource("data_withNanoOfDay")
    public void test_withNanoOfDay(
            long mjd,
            long nanos,
            long newNanoOfDay,
            @Nullable Long expectedMjd,
            long expectedNanos) {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(mjd, nanos);
        if (expectedMjd != null) {
            UtcInstant withNanoOfDay = instant.withNanoOfDay(newNanoOfDay);
            assertEquals(expectedMjd.longValue(), withNanoOfDay.getModifiedJulianDay());
            assertEquals(expectedNanos, withNanoOfDay.getNanoOfDay());
        } else {
            assertThrows(DateTimeException.class, () -> instant.withNanoOfDay(newNanoOfDay));
        }
    }
}
