package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hours#toDuration()} (and the deprecated {@link Hours#toPeriod()})
 * produce a {@link Duration} holding the same number of hours as the source {@code Hours}.
 */
public class TestHours_test_toDuration {

    @SuppressWarnings("deprecation")
    @Test
    public void test_toDuration() {
        // Cover a representative range of negative, zero and positive hour amounts.
        for (int hours = -20; hours < 20; hours++) {
            Duration expected = Duration.ofHours(hours);

            assertEquals(expected, Hours.of(hours).toPeriod());
            assertEquals(expected, Hours.of(hours).toDuration());
        }
    }
}
