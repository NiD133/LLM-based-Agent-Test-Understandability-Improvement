package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link UtcInstant#toString()}.
 * <p>
 * A {@code UtcInstant} is built from a Modified Julian Day (MJD) and a
 * nanosecond-of-day, and {@code toString()} renders it as an ISO-8601 string
 * ending in {@code 'Z'}. These tests pin down that rendering across a range of
 * cases: the epoch, fractional-second formatting at nanosecond/microsecond/
 * millisecond precision, whole hour/minute/second carries, and the leap second
 * {@code 23:59:60}.
 */
public class TestUtcInstant_test_toString {

    /** Nanoseconds in one second. */
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    /** Seconds in a normal (non-leap) day. */
    private static final long SECS_PER_DAY = 24L * 60 * 60;
    /** Nanoseconds in a normal (non-leap) day. */
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    /** MJD for 1972-12-31, a day that ends with a leap second. */
    private static final long MJD_1972_12_31_LEAP = 41682;
    /** MJD for 1973-01-01. */
    private static final long MJD_1973_01_01 = 41683;

    /**
     * Cases of {modifiedJulianDay, nanoOfDay, expected toString()}.
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            // epoch and basic fractional-second precision
            {40587, 0, "1970-01-01T00:00:00Z"},
            {40588, 1, "1970-01-02T00:00:00.000000001Z"},
            {40588, 999, "1970-01-02T00:00:00.000000999Z"},
            {40588, 1000, "1970-01-02T00:00:00.000001Z"},
            {40588, 999000, "1970-01-02T00:00:00.000999Z"},
            {40588, 1000000, "1970-01-02T00:00:00.001Z"},
            {40618, 999999999, "1970-02-01T00:00:00.999999999Z"},
            // whole second, minute and hour carries
            {40619, 1 * NANOS_PER_SEC, "1970-02-02T00:00:01Z"},
            {40620, 60L * NANOS_PER_SEC, "1970-02-03T00:01:00Z"},
            {40621, 60L * 60L * NANOS_PER_SEC, "1970-02-04T01:00:00Z"},
            // the second before, during, and after a leap second
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - NANOS_PER_SEC, "1972-12-31T23:59:59Z"},
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY, "1972-12-31T23:59:60Z"},
            {MJD_1973_01_01, 0, "1973-01-01T00:00:00Z"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(long mjd, long nanoOfDay, String expected) {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(mjd, nanoOfDay);

        assertEquals(expected, instant.toString());
    }
}
