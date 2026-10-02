package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestHours_test_toDuration {

    private static final int MIN_TESTED_HOURS = -20;
    private static final int MAX_TESTED_HOURS_EXCLUSIVE = 20;

    @SuppressWarnings("deprecation")
    @Test
    public void test_toDuration() {
        for (int hourAmount = MIN_TESTED_HOURS; hourAmount < MAX_TESTED_HOURS_EXCLUSIVE; hourAmount++) {
            assertEquals(Duration.ofHours(hourAmount), Hours.of(hourAmount).toPeriod());
            assertEquals(Duration.ofHours(hourAmount), Hours.of(hourAmount).toDuration());
        }
    }
}
