package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestUtcInstant_test_toString {

    // Modified Julian Day values for dates used in the test data
    private static final long MJD_1970_01_01      = 40587; // Unix epoch
    private static final long MJD_1970_01_02      = 40588;
    private static final long MJD_1970_02_01      = 40618;
    private static final long MJD_1970_02_02      = 40619;
    private static final long MJD_1970_02_03      = 40620;
    private static final long MJD_1970_02_04      = 40621;
    private static final long MJD_1972_12_31_LEAP = 41682; // positive leap-second day
    private static final long MJD_1973_01_01      = 41683;

    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long SECS_PER_DAY  = 24L * 60 * 60;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    public static Object[][] data_toString() {
        return new Object[][] {
            // Unix epoch
            { MJD_1970_01_01, 0L, "1970-01-01T00:00:00Z" },

            // Sub-second precision: trailing zeros are trimmed to milliseconds, microseconds, or nanoseconds
            { MJD_1970_01_02,           1L, "1970-01-02T00:00:00.000000001Z" },
            { MJD_1970_01_02,         999L, "1970-01-02T00:00:00.000000999Z" },
            { MJD_1970_01_02,       1_000L, "1970-01-02T00:00:00.000001Z"    },
            { MJD_1970_01_02,     999_000L, "1970-01-02T00:00:00.000999Z"    },
            { MJD_1970_01_02,   1_000_000L, "1970-01-02T00:00:00.001Z"       },
            { MJD_1970_02_01, 999_999_999L, "1970-02-01T00:00:00.999999999Z" },

            // Whole-second, minute, and hour boundaries (no fractional seconds in output)
            { MJD_1970_02_02,      NANOS_PER_SEC,         "1970-02-02T00:00:01Z" },
            { MJD_1970_02_03, 60 * NANOS_PER_SEC,         "1970-02-03T00:01:00Z" },
            { MJD_1970_02_04, 60L * 60 * NANOS_PER_SEC,   "1970-02-04T01:00:00Z" },

            // Leap-second day (1972-12-31): second just before the leap, the leap second itself,
            // and the first instant of the following day
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY - NANOS_PER_SEC, "1972-12-31T23:59:59Z" },
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY,                  "1972-12-31T23:59:60Z" },
            { MJD_1973_01_01,      0L,                              "1973-01-01T00:00:00Z" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(long mjd, long nod, String expected) {
        assertEquals(expected, UtcInstant.ofModifiedJulianDay(mjd, nod).toString());
    }
}
