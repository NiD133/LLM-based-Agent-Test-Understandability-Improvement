package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofHours {

    private static final int SECONDS_PER_HOUR = 3600;

    @Test
    public void test_ofHours() {
        // Zero hours maps to zero seconds
        assertEquals(0, Seconds.ofHours(0).getAmount());

        // Positive hours: 1 h → 3600 s, 2 h → 7200 s
        assertEquals(SECONDS_PER_HOUR, Seconds.ofHours(1).getAmount());
        assertEquals(2 * SECONDS_PER_HOUR, Seconds.ofHours(2).getAmount());

        // Largest positive input that does not overflow int
        int maxSafeHours = Integer.MAX_VALUE / SECONDS_PER_HOUR;
        assertEquals(maxSafeHours * SECONDS_PER_HOUR, Seconds.ofHours(maxSafeHours).getAmount());

        // Negative hours: -1 h → -3600 s, -2 h → -7200 s
        assertEquals(-SECONDS_PER_HOUR, Seconds.ofHours(-1).getAmount());
        assertEquals(-2 * SECONDS_PER_HOUR, Seconds.ofHours(-2).getAmount());

        // Largest negative input that does not overflow int
        int minSafeHours = Integer.MIN_VALUE / SECONDS_PER_HOUR;
        assertEquals(minSafeHours * SECONDS_PER_HOUR, Seconds.ofHours(minSafeHours).getAmount());
    }
}
