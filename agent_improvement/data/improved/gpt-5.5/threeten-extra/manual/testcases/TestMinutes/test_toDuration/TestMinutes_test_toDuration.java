package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_toDuration {

    private static final int FIRST_TESTED_MINUTE = -20;
    private static final int MINUTE_AFTER_LAST_TESTED = 20;

    @Test
    public void test_toDuration() {
        for (int minutes = FIRST_TESTED_MINUTE; minutes < MINUTE_AFTER_LAST_TESTED; minutes++) {
            assertEquals(Duration.ofMinutes(minutes), Minutes.of(minutes).toDuration());
        }
    }
}
