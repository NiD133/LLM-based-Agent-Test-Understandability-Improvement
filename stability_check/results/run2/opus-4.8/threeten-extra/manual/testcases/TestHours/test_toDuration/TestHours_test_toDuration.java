package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours} converts to a {@link Duration} of the same
 * number of hours, covering both the current {@code toDuration()} method and
 * the deprecated {@code toPeriod()} alias.
 */
public class TestHours_test_toDuration {

    /**
     * For every hour count in a small range around zero (negative, zero and
     * positive), both conversion methods must yield a duration holding exactly
     * that many hours.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void test_toDuration() {
        for (int hourCount = -20; hourCount < 20; hourCount++) {
            Hours hours = Hours.of(hourCount);
            Duration expected = Duration.ofHours(hourCount);

            assertEquals(expected, hours.toPeriod());
            assertEquals(expected, hours.toDuration());
        }
    }
}
