package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_ofHours {

    private static final int MINUTES_PER_HOUR = 60;

    @Test
    public void test_ofHours() {
        assertMinutesFromHours(0, 0);
        assertMinutesFromHours(60, 1);
        assertMinutesFromHours(120, 2);
        assertMinutesFromHours((Integer.MAX_VALUE / MINUTES_PER_HOUR) * MINUTES_PER_HOUR,
                Integer.MAX_VALUE / MINUTES_PER_HOUR);

        assertMinutesFromHours(-60, -1);
        assertMinutesFromHours(-120, -2);
        assertMinutesFromHours((Integer.MIN_VALUE / MINUTES_PER_HOUR) * MINUTES_PER_HOUR,
                Integer.MIN_VALUE / MINUTES_PER_HOUR);
    }

    private static void assertMinutesFromHours(int expectedMinutes, int hours) {
        assertEquals(expectedMinutes, Minutes.ofHours(hours).getAmount());
    }
}
