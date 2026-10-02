package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestUtcInstant_test_withModifiedJulianDay {

    private static final long MJD_1972_12_30 = 41681;
    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01 = 41683;
    private static final long MJD_1973_12_31_LEAP = MJD_1972_12_31_LEAP + 365;

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1000000000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    @Nullable
    public static Object[][] data_withModifiedJulianDay() {
        return new @Nullable Object[][] {
            { 0L, 12345L, 1L, 1L, 12345L },
            { 0L, 12345L, -1L, -1L, 12345L },
            { 7L, 12345L, 2L, 2L, 12345L },
            { 7L, 12345L, -2L, -2L, 12345L },
            { -99L, 12345L, 3L, 3L, 12345L },
            { -99L, 12345L, -3L, -3L, 12345L },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_30, null, 0L },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_31_LEAP, MJD_1972_12_31_LEAP, NANOS_PER_DAY },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_01_01, null, 0L },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_12_31_LEAP, MJD_1973_12_31_LEAP, NANOS_PER_DAY },
        };
    }

    @ParameterizedTest
    @MethodSource("data_withModifiedJulianDay")
    public void test_withModifiedJulianDay(
            long originalMjd,
            long originalNanos,
            long replacementMjd,
            @Nullable Long expectedMjd,
            long expectedNanos) {

        UtcInstant instant = UtcInstant.ofModifiedJulianDay(originalMjd, originalNanos);

        if (expectedMjd == null) {
            assertInvalidModifiedJulianDay(instant, replacementMjd);
        } else {
            assertModifiedJulianDayChanged(instant, replacementMjd, expectedMjd.longValue(), expectedNanos);
        }
    }

    private static void assertModifiedJulianDayChanged(
            UtcInstant instant,
            long replacementMjd,
            long expectedMjd,
            long expectedNanos) {

        UtcInstant withModifiedJulianDay = instant.withModifiedJulianDay(replacementMjd);

        assertEquals(expectedMjd, withModifiedJulianDay.getModifiedJulianDay());
        assertEquals(expectedNanos, withModifiedJulianDay.getNanoOfDay());
    }

    private static void assertInvalidModifiedJulianDay(UtcInstant instant, long replacementMjd) {
        assertThrows(DateTimeException.class, () -> instant.withModifiedJulianDay(replacementMjd));
    }
}
