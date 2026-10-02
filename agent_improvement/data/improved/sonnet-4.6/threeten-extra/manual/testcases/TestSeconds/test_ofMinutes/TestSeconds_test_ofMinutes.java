package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofMinutes {

    private static final int SECONDS_PER_MINUTE = 60;

    @Test
    public void test_ofMinutes() {
        // zero minutes yields zero seconds
        assertEquals(0, Seconds.ofMinutes(0).getAmount());

        // positive minutes are multiplied by 60
        assertEquals(60, Seconds.ofMinutes(1).getAmount());
        assertEquals(120, Seconds.ofMinutes(2).getAmount());

        // largest number of minutes that won't overflow when converted to seconds
        int maxMinutes = Integer.MAX_VALUE / SECONDS_PER_MINUTE;
        assertEquals(maxMinutes * SECONDS_PER_MINUTE, Seconds.ofMinutes(maxMinutes).getAmount());

        // negative minutes produce negative seconds
        assertEquals(-60, Seconds.ofMinutes(-1).getAmount());
        assertEquals(-120, Seconds.ofMinutes(-2).getAmount());

        // most-negative number of minutes that won't overflow when converted to seconds
        int minMinutes = Integer.MIN_VALUE / SECONDS_PER_MINUTE;
        assertEquals(minMinutes * SECONDS_PER_MINUTE, Seconds.ofMinutes(minMinutes).getAmount());
    }
}
