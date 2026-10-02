package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestUtcInstant_test_minus {

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    public static Object[][] data_minus() {
        return new Object[][] {
                {0, 0, 2 * SECS_PER_DAY, -5, -2, 5},
                {0, 0, 1 * SECS_PER_DAY, -1, -1, 1},
                {0, 0, 1 * SECS_PER_DAY, 0, -1, 0},
                {0, 0, 0, 2, -1, NANOS_PER_DAY - 2},
                {0, 0, 0, 1, -1, NANOS_PER_DAY - 1},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 1},
                {0, 0, 0, -2, 0, 2},
                {0, 0, -1, 0, 0, 1 * NANOS_PER_SEC},
                {0, 0, -2, 0, 0, 2 * NANOS_PER_SEC},
                {0, 0, -3, -333_333_333, 0, 3 * NANOS_PER_SEC + 333_333_333},
                {0, 0, -1 * SECS_PER_DAY, 0, 1, 0},
                {0, 0, -1 * SECS_PER_DAY, -1, 1, 1},
                {0, 0, -2 * SECS_PER_DAY, -5, 2, 5},
                {1, 0, 2 * SECS_PER_DAY, -5, -1, 5},
                {1, 0, 1 * SECS_PER_DAY, -1, 0, 1},
                {1, 0, 1 * SECS_PER_DAY, 0, 0, 0},
                {1, 0, 0, 2, 0, NANOS_PER_DAY - 2},
                {1, 0, 0, 1, 0, NANOS_PER_DAY - 1},
                {1, 0, 0, 0, 1, 0},
                {1, 0, 0, -1, 1, 1},
                {1, 0, 0, -2, 1, 2},
                {1, 0, -1, 0, 1, 1 * NANOS_PER_SEC},
                {1, 0, -2, 0, 1, 2 * NANOS_PER_SEC},
                {1, 0, -3, -333_333_333, 1, 3 * NANOS_PER_SEC + 333_333_333},
                {1, 0, -1 * SECS_PER_DAY, 0, 2, 0},
                {1, 0, -1 * SECS_PER_DAY, -1, 2, 1},
                {1, 0, -2 * SECS_PER_DAY, -5, 3, 5},
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus(
            long startMjd,
            long startNanoOfDay,
            long durationSeconds,
            int durationNanos,
            long expectedMjd,
            long expectedNanoOfDay) {

        UtcInstant actual = UtcInstant.ofModifiedJulianDay(startMjd, startNanoOfDay)
                .minus(Duration.ofSeconds(durationSeconds, durationNanos));

        assertUtcInstant(actual, expectedMjd, expectedNanoOfDay);
    }

    private static void assertUtcInstant(UtcInstant actual, long expectedMjd, long expectedNanoOfDay) {
        assertEquals(expectedMjd, actual.getModifiedJulianDay());
        assertEquals(expectedNanoOfDay, actual.getNanoOfDay());
    }
}
