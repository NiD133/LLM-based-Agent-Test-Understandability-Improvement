package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link UtcInstant#toString()} produces valid ISO-8601 strings that
 * {@link UtcInstant#parse(CharSequence)} can round-trip back to an equal instant.
 */
public class TestUtcInstant_test_toString_parse {

    // MJD of the Unix epoch (1970-01-01) and key dates around the 1972-12-31 leap second
    private static final long MJD_UNIX_EPOCH      = 40587;
    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01      = 41683;

    private static final long SECS_PER_DAY  = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    /**
     * Test data: (mjd, nanoOfDay, expectedIsoString).
     * Covers nanosecond-precision formatting and the 1972-12-31 positive leap second.
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            // --- nanosecond precision: trailing zeros are dropped at ms/µs/ns boundaries ---
            { MJD_UNIX_EPOCH,     0L,                        "1970-01-01T00:00:00Z"            }, // Unix epoch
            { MJD_UNIX_EPOCH + 1, 1L,                        "1970-01-02T00:00:00.000000001Z"  }, // 1 ns
            { MJD_UNIX_EPOCH + 1, 999L,                      "1970-01-02T00:00:00.000000999Z"  }, // 999 ns
            { MJD_UNIX_EPOCH + 1, 1_000L,                    "1970-01-02T00:00:00.000001Z"     }, // 1 µs  (6-digit form)
            { MJD_UNIX_EPOCH + 1, 999_000L,                  "1970-01-02T00:00:00.000999Z"     }, // 999 µs
            { MJD_UNIX_EPOCH + 1, 1_000_000L,                "1970-01-02T00:00:00.001Z"        }, // 1 ms  (3-digit form)
            { MJD_UNIX_EPOCH + 31, 999_999_999L,             "1970-02-01T00:00:00.999999999Z"  }, // max 9-digit fraction
            // --- whole-second and sub-minute boundaries ---
            { MJD_UNIX_EPOCH + 32, 1_000_000_000L,           "1970-02-02T00:00:01Z"            }, // exactly 1 second
            { MJD_UNIX_EPOCH + 33, 60L  * NANOS_PER_SEC,     "1970-02-03T00:01:00Z"            }, // 1 minute
            { MJD_UNIX_EPOCH + 34, 3600L * NANOS_PER_SEC,    "1970-02-04T01:00:00Z"            }, // 1 hour
            // --- 1972-12-31 positive leap second (23:59:60) ---
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY - NANOS_PER_SEC, "1972-12-31T23:59:59Z"       }, // last normal second
            { MJD_1972_12_31_LEAP, NANOS_PER_DAY,                 "1972-12-31T23:59:60Z"       }, // the leap second itself
            { MJD_1973_01_01,      0L,                             "1973-01-01T00:00:00Z"       }, // midnight after the leap
        };
    }

    /**
     * Verifies that constructing a {@link UtcInstant} from (mjd, nanoOfDay) and parsing
     * the expected ISO-8601 string yield equal instants, confirming both
     * {@code toString()} formatting and {@code parse()} round-trip correctness.
     */
    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString_parse(long mjd, long nanoOfDay, String expectedIsoString) {
        assertEquals(UtcInstant.ofModifiedJulianDay(mjd, nanoOfDay), UtcInstant.parse(expectedIsoString));
    }
}
