package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestHours_test_toDuration {

    private static final int FIRST_TESTED_HOUR = -20;
    private static final int HOURS_LIMIT_EXCLUSIVE = 20;

    @SuppressWarnings("deprecation")
    @Test
    public void test_toDuration() {
        for (int hourAmount = FIRST_TESTED_HOUR; hourAmount < HOURS_LIMIT_EXCLUSIVE; hourAmount++) {
            assertDurationConversionMatchesJavaDuration(hourAmount);
        }
    }

    @SuppressWarnings("deprecation")
    private static void assertDurationConversionMatchesJavaDuration(int hourAmount) {
        assertEquals(Duration.ofHours(hourAmount), Hours.of(hourAmount).toPeriod());
        assertEquals(Duration.ofHours(hourAmount), Hours.of(hourAmount).toDuration());
    }
}
