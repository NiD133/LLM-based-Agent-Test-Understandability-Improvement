package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofHours {

    private static final int SECONDS_PER_HOUR = 3600;

    @Test
    public void test_ofHours() {
        assertOfHoursConvertsToSeconds(0, 0);
        assertOfHoursConvertsToSeconds(1, SECONDS_PER_HOUR);
        assertOfHoursConvertsToSeconds(2, 2 * SECONDS_PER_HOUR);
        assertOfHoursConvertsToSeconds(
                Integer.MAX_VALUE / SECONDS_PER_HOUR,
                (Integer.MAX_VALUE / SECONDS_PER_HOUR) * SECONDS_PER_HOUR);
        assertOfHoursConvertsToSeconds(-1, -SECONDS_PER_HOUR);
        assertOfHoursConvertsToSeconds(-2, -2 * SECONDS_PER_HOUR);
        assertOfHoursConvertsToSeconds(
                Integer.MIN_VALUE / SECONDS_PER_HOUR,
                (Integer.MIN_VALUE / SECONDS_PER_HOUR) * SECONDS_PER_HOUR);
    }

    private static void assertOfHoursConvertsToSeconds(int hours, int expectedSeconds) {
        assertEquals(expectedSeconds, Seconds.ofHours(hours).getAmount());
    }
}
