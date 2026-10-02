package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies that an {@link Hours} amount is converted to the equivalent
 * {@link Duration}, i.e. a duration holding the same number of hours.
 * <p>
 * Both the current {@link Hours#toDuration()} method and its deprecated
 * predecessor {@link Hours#toPeriod()} are expected to behave identically.
 */
public class TestHours_test_toDuration {

    @SuppressWarnings("deprecation")
    @Test
    public void test_toDuration() {
        // Exercise a range that spans negative, zero and positive hour amounts.
        for (int hourAmount = -20; hourAmount < 20; hourAmount++) {
            Hours hours = Hours.of(hourAmount);
            Duration expectedDuration = Duration.ofHours(hourAmount);

            assertEquals(expectedDuration, hours.toPeriod());
            assertEquals(expectedDuration, hours.toDuration());
        }
    }
}
