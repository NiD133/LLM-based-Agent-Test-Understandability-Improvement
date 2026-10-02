package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestHours_test_toDuration {

    private static final int FIRST_TESTED_HOUR = -20;
    private static final int FIRST_UNTESTED_HOUR = 20;

    @SuppressWarnings("deprecation")
    @Test
    public void test_toDuration() {
        for (int hours = FIRST_TESTED_HOUR; hours < FIRST_UNTESTED_HOUR; hours++) {
            assertConvertsToDuration(hours);
        }
    }

    @SuppressWarnings("deprecation")
    private void assertConvertsToDuration(int hours) {
        assertEquals(Duration.ofHours(hours), Hours.of(hours).toPeriod());
        assertEquals(Duration.ofHours(hours), Hours.of(hours).toDuration());
    }
}
