package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofMinutes {

    private static final int SECONDS_PER_MINUTE = 60;

    @Test
    public void test_ofMinutes() {
        assertMinutesConvertToSeconds(0, 0);
        assertMinutesConvertToSeconds(1, SECONDS_PER_MINUTE);
        assertMinutesConvertToSeconds(2, 2 * SECONDS_PER_MINUTE);

        int largestWholeMinuteCount = Integer.MAX_VALUE / SECONDS_PER_MINUTE;
        assertMinutesConvertToSeconds(largestWholeMinuteCount, largestWholeMinuteCount * SECONDS_PER_MINUTE);

        assertMinutesConvertToSeconds(-1, -SECONDS_PER_MINUTE);
        assertMinutesConvertToSeconds(-2, -2 * SECONDS_PER_MINUTE);

        int smallestWholeMinuteCount = Integer.MIN_VALUE / SECONDS_PER_MINUTE;
        assertMinutesConvertToSeconds(smallestWholeMinuteCount, smallestWholeMinuteCount * SECONDS_PER_MINUTE);
    }

    private void assertMinutesConvertToSeconds(int minutes, int expectedSeconds) {
        assertEquals(expectedSeconds, Seconds.ofMinutes(minutes).getAmount());
    }
}
