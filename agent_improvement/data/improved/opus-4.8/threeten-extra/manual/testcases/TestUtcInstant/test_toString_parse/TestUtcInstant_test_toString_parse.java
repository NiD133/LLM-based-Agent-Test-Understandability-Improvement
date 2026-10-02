package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link UtcInstant#parse(CharSequence)} reads an ISO-8601 instant string
 * back into the same {@code UtcInstant} that {@link UtcInstant#ofModifiedJulianDay(long, long)}
 * builds from the matching Modified Julian Day and nano-of-day.
 */
public class TestUtcInstant_test_toString_parse {

    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    /** Modified Julian Day for 1972-12-31, the day that carries a positive leap second. */
    private static final long MJD_1972_12_31_LEAP = 41682;
    /** Modified Julian Day for 1973-01-01. */
    private static final long MJD_1973_01_01 = 41683;

    /**
     * Each row is: Modified Julian Day, nano-of-day, and the canonical ISO-8601 text
     * that should parse into the instant defined by those two numbers.
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            {40587, 0,                          "1970-01-01T00:00:00Z"},
            {40588, 1,                          "1970-01-02T00:00:00.000000001Z"},
            {40588, 999,                        "1970-01-02T00:00:00.000000999Z"},
            {40588, 1000,                       "1970-01-02T00:00:00.000001Z"},
            {40588, 999000,                     "1970-01-02T00:00:00.000999Z"},
            {40588, 1000000,                    "1970-01-02T00:00:00.001Z"},
            {40618, 999999999,                  "1970-02-01T00:00:00.999999999Z"},
            {40619, 1 * NANOS_PER_SEC,          "1970-02-02T00:00:01Z"},
            {40620, 60L * NANOS_PER_SEC,        "1970-02-03T00:01:00Z"},
            {40621, 60L * 60L * NANOS_PER_SEC,  "1970-02-04T01:00:00Z"},
            // last ordinary second of the leap day
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY - NANOS_PER_SEC, "1972-12-31T23:59:59Z"},
            // the inserted leap second itself, shown as 23:59:60
            {MJD_1972_12_31_LEAP, NANOS_PER_DAY,                 "1972-12-31T23:59:60Z"},
            {MJD_1973_01_01, 0,                                 "1973-01-01T00:00:00Z"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void parse_returnsInstantBuiltFromMjdAndNanoOfDay(long mjd, long nanoOfDay, String isoText) {
        UtcInstant expected = UtcInstant.ofModifiedJulianDay(mjd, nanoOfDay);
        assertEquals(expected, UtcInstant.parse(isoText));
    }
}
