package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_toDuration {

    private static final int FIRST_TESTED_SECOND = -20;
    private static final int FIRST_UNTESTED_SECOND = 20;

    @Test
    public void test_toDuration() {
        for (int seconds = FIRST_TESTED_SECOND; seconds < FIRST_UNTESTED_SECOND; seconds++) {
            Duration expectedDuration = Duration.ofSeconds(seconds);
            Duration actualDuration = Seconds.of(seconds).toDuration();

            assertEquals(expectedDuration, actualDuration);
        }
    }
}
