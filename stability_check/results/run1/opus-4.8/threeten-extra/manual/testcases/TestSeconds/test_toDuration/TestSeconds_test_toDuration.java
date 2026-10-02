package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Seconds#toDuration()} returns a {@link Duration}
 * holding exactly the same number of seconds.
 */
public class TestSeconds_test_toDuration {

    @Test
    public void toDuration_matchesDurationOfSameSeconds() {
        // Check a representative range covering negative, zero and positive values.
        for (int secondsValue = -20; secondsValue < 20; secondsValue++) {
            Duration expected = Duration.ofSeconds(secondsValue);
            Duration actual = Seconds.of(secondsValue).toDuration();

            assertEquals(expected, actual);
        }
    }
}
