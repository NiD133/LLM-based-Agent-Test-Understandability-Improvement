package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("UtcInstant.withModifiedJulianDay")
public class TestUtcInstant_test_withModifiedJulianDay {

    // MJD values for dates around the 1972-12-31 leap second
    private static final long MJD_1972_12_30      = 41681;
    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01      = 41683;
    private static final long MJD_1973_12_31_LEAP = MJD_1972_12_31_LEAP + 365;

    private static final long SECS_PER_DAY       = 24L * 60 * 60;
    private static final long NANOS_PER_SEC       = 1_000_000_000L;
    private static final long NANOS_PER_DAY       = SECS_PER_DAY * NANOS_PER_SEC;

    /**
     * Test data columns:
     *   baseMjd       – MJD of the starting instant
     *   baseNanos     – nano-of-day of the starting instant
     *   newMjd        – argument passed to withModifiedJulianDay()
     *   expectedMjd   – expected MJD of the result, or null when a DateTimeException is expected
     *   expectedNanos – expected nano-of-day of the result (ignored when expectedMjd is null)
     */
    @Nullable
    public static Object[][] data_withModifiedJulianDay() {
        return new @Nullable Object[][] {
            // Ordinary days: nanos are preserved regardless of which MJD the instant moves to
            { 0L,   12345L, 1L,  1L,  12345L },
            { 0L,   12345L, -1L, -1L, 12345L },
            { 7L,   12345L, 2L,  2L,  12345L },
            { 7L,   12345L, -2L, -2L, 12345L },
            { -99L, 12345L, 3L,  3L,  12345L },
            { -99L, 12345L, -3L, -3L, 12345L },

            // Leap-second nanos (NANOS_PER_DAY) carried into a non-leap day → DateTimeException
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_30,      null,                  0L },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_01_01,      null,                  0L },

            // Leap-second nanos carried into another leap day → valid
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1972_12_31_LEAP, MJD_1972_12_31_LEAP, NANOS_PER_DAY },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY, MJD_1973_12_31_LEAP, MJD_1973_12_31_LEAP, NANOS_PER_DAY },
        };
    }

    @ParameterizedTest
    @MethodSource("data_withModifiedJulianDay")
    public void test_withModifiedJulianDay(
            long baseMjd, long baseNanos, long newMjd,
            @Nullable Long expectedMjd, long expectedNanos) {

        UtcInstant base = UtcInstant.ofModifiedJulianDay(baseMjd, baseNanos);

        if (expectedMjd != null) {
            UtcInstant result = base.withModifiedJulianDay(newMjd);
            assertEquals(expectedMjd.longValue(), result.getModifiedJulianDay());
            assertEquals(expectedNanos, result.getNanoOfDay());
        } else {
            assertThrows(DateTimeException.class, () -> base.withModifiedJulianDay(newMjd));
        }
    }
}
