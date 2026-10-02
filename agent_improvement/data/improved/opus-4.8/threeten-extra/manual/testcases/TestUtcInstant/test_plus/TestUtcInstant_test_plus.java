package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link UtcInstant#plus(Duration)}.
 */
public class TestUtcInstant_test_plus {

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    /**
     * Cases for {@link #test_plus}.
     * <p>
     * Each row is:
     * <ol>
     *   <li>{@code mjd}          - starting Modified Julian Day</li>
     *   <li>{@code nanos}        - starting nano-of-day</li>
     *   <li>{@code plusSeconds}  - seconds of the duration to add</li>
     *   <li>{@code plusNanos}    - nanos of the duration to add</li>
     *   <li>{@code expectedMjd}  - expected resulting Modified Julian Day</li>
     *   <li>{@code expectedNanos} - expected resulting nano-of-day</li>
     * </ol>
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // starting at MJD 0, nano-of-day 0
            {0, 0, -2 * SECS_PER_DAY, 5, -2, 5},
            {0, 0, -1 * SECS_PER_DAY, 1, -1, 1},
            {0, 0, -1 * SECS_PER_DAY, 0, -1, 0},
            {0, 0, 0, -2, -1, NANOS_PER_DAY - 2},
            {0, 0, 0, -1, -1, NANOS_PER_DAY - 1},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 1},
            {0, 0, 0, 2, 0, 2},
            {0, 0, 1, 0, 0, 1 * NANOS_PER_SEC},
            {0, 0, 2, 0, 0, 2 * NANOS_PER_SEC},
            {0, 0, 3, 333333333, 0, 3 * NANOS_PER_SEC + 333333333},
            {0, 0, 1 * SECS_PER_DAY, 0, 1, 0},
            {0, 0, 1 * SECS_PER_DAY, 1, 1, 1},
            {0, 0, 2 * SECS_PER_DAY, 5, 2, 5},
            // starting at MJD 1, nano-of-day 0
            {1, 0, -2 * SECS_PER_DAY, 5, -1, 5},
            {1, 0, -1 * SECS_PER_DAY, 1, 0, 1},
            {1, 0, -1 * SECS_PER_DAY, 0, 0, 0},
            {1, 0, 0, -2, 0, NANOS_PER_DAY - 2},
            {1, 0, 0, -1, 0, NANOS_PER_DAY - 1},
            {1, 0, 0, 0, 1, 0},
            {1, 0, 0, 1, 1, 1},
            {1, 0, 0, 2, 1, 2},
            {1, 0, 1, 0, 1, 1 * NANOS_PER_SEC},
            {1, 0, 2, 0, 1, 2 * NANOS_PER_SEC},
            {1, 0, 3, 333333333, 1, 3 * NANOS_PER_SEC + 333333333},
            {1, 0, 1 * SECS_PER_DAY, 0, 2, 0},
            {1, 0, 1 * SECS_PER_DAY, 1, 2, 1},
            {1, 0, 2 * SECS_PER_DAY, 5, 3, 5},
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus(long mjd, long nanos, long plusSeconds, int plusNanos, long expectedMjd, long expectedNanos) {
        UtcInstant start = UtcInstant.ofModifiedJulianDay(mjd, nanos);

        UtcInstant result = start.plus(Duration.ofSeconds(plusSeconds, plusNanos));

        assertEquals(expectedMjd, result.getModifiedJulianDay());
        assertEquals(expectedNanos, result.getNanoOfDay());
    }
}
