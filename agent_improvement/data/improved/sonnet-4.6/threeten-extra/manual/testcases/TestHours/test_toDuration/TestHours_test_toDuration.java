package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestHours_test_toDuration {

    // Tests cover the range of negative, zero, and positive hour values (-20 to 19).

    @Test
    public void test_toDuration() {
        for (int i = -20; i < 20; i++) {
            assertEquals(Duration.ofHours(i), Hours.of(i).toDuration(),
                    "toDuration() should return a Duration equal to the hour count for i=" + i);
        }
    }

    @SuppressWarnings("deprecation") // toPeriod() is the deprecated predecessor of toDuration()
    @Test
    public void test_toPeriod_deprecatedAlias() {
        for (int i = -20; i < 20; i++) {
            assertEquals(Duration.ofHours(i), Hours.of(i).toPeriod(),
                    "toPeriod() (deprecated) should return the same Duration as toDuration() for i=" + i);
        }
    }
}
