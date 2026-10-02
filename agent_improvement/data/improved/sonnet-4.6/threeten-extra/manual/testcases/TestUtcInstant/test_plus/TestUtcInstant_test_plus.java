package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestUtcInstant_test_plus {

    // Convenient time-unit constants used to build the test data table
    private static final long SECS_PER_DAY  = 24L * 60 * 60;
    private static final long NANOS_PER_SEC  = 1_000_000_000L;
    private static final long NANOS_PER_DAY  = SECS_PER_DAY * NANOS_PER_SEC;

    /**
     * Test data for {@link #test_plus}.
     *
     * <p>Each row describes one call to {@code UtcInstant.plus(Duration)} and its expected outcome:
     * <pre>
     *   col 0  mjd          – Modified Julian Day of the starting instant
     *   col 1  nanos        – nanosecond-of-day of the starting instant
     *   col 2  plusSeconds  – seconds component of the Duration to add
     *   col 3  plusNanos    – nanosecond adjustment component of the Duration to add
     *   col 4  expectedMjd  – expected Modified Julian Day after the addition
     *   col 5  expectedNanos– expected nanosecond-of-day after the addition
     * </pre>
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // Starting at MJD=0, nanoOfDay=0 ─ add a negative multi-day duration
            { 0, 0,  -2 * SECS_PER_DAY,         5,   -2,                      5 },
            { 0, 0,  -1 * SECS_PER_DAY,         1,   -1,                      1 },
            { 0, 0,  -1 * SECS_PER_DAY,         0,   -1,                      0 },

            // Starting at MJD=0, nanoOfDay=0 ─ add a sub-second duration (negative nanos wrap to previous day)
            { 0, 0,   0,                        -2,   -1,   NANOS_PER_DAY - 2     },
            { 0, 0,   0,                        -1,   -1,   NANOS_PER_DAY - 1     },

            // Starting at MJD=0, nanoOfDay=0 ─ identity and small positive additions
            { 0, 0,   0,                         0,    0,                      0 },
            { 0, 0,   0,                         1,    0,                      1 },
            { 0, 0,   0,                         2,    0,                      2 },

            // Starting at MJD=0, nanoOfDay=0 ─ add whole seconds
            { 0, 0,   1,                         0,    0,   1 * NANOS_PER_SEC     },
            { 0, 0,   2,                         0,    0,   2 * NANOS_PER_SEC     },
            { 0, 0,   3,                 333333333,    0,   3 * NANOS_PER_SEC + 333333333 },

            // Starting at MJD=0, nanoOfDay=0 ─ add a positive multi-day duration
            { 0, 0,   1 * SECS_PER_DAY,          0,    1,                      0 },
            { 0, 0,   1 * SECS_PER_DAY,          1,    1,                      1 },
            { 0, 0,   2 * SECS_PER_DAY,          5,    2,                      5 },

            // Starting at MJD=1, nanoOfDay=0 ─ add a negative multi-day duration
            { 1, 0,  -2 * SECS_PER_DAY,          5,   -1,                      5 },
            { 1, 0,  -1 * SECS_PER_DAY,          1,    0,                      1 },
            { 1, 0,  -1 * SECS_PER_DAY,          0,    0,                      0 },

            // Starting at MJD=1, nanoOfDay=0 ─ add a sub-second duration (negative nanos wrap to previous day)
            { 1, 0,   0,                         -2,    0,   NANOS_PER_DAY - 2    },
            { 1, 0,   0,                         -1,    0,   NANOS_PER_DAY - 1    },

            // Starting at MJD=1, nanoOfDay=0 ─ identity and small positive additions
            { 1, 0,   0,                          0,    1,                      0 },
            { 1, 0,   0,                          1,    1,                      1 },
            { 1, 0,   0,                          2,    1,                      2 },

            // Starting at MJD=1, nanoOfDay=0 ─ add whole seconds
            { 1, 0,   1,                          0,    1,   1 * NANOS_PER_SEC    },
            { 1, 0,   2,                          0,    1,   2 * NANOS_PER_SEC    },
            { 1, 0,   3,                  333333333,    1,   3 * NANOS_PER_SEC + 333333333 },

            // Starting at MJD=1, nanoOfDay=0 ─ add a positive multi-day duration
            { 1, 0,   1 * SECS_PER_DAY,           0,    2,                      0 },
            { 1, 0,   1 * SECS_PER_DAY,           1,    2,                      1 },
            { 1, 0,   2 * SECS_PER_DAY,           5,    3,                      5 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus(long mjd, long nanos, long plusSeconds, int plusNanos,
                          long expectedMjd, long expectedNanos) {
        UtcInstant result = UtcInstant.ofModifiedJulianDay(mjd, nanos)
                .plus(Duration.ofSeconds(plusSeconds, plusNanos));
        assertEquals(expectedMjd,   result.getModifiedJulianDay());
        assertEquals(expectedNanos, result.getNanoOfDay());
    }
}
