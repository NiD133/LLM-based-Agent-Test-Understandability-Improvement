package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Seconds#toDuration()} produces a {@link Duration} holding
 * the same number of seconds as the originating {@code Seconds} amount.
 */
public class TestSeconds_test_toDuration {

    @Test
    public void test_toDuration() {
        // Cover a representative range of negative, zero and positive amounts.
        for (int secondsAmount = -20; secondsAmount < 20; secondsAmount++) {
            Duration expected = Duration.ofSeconds(secondsAmount);
            Duration actual = Seconds.of(secondsAmount).toDuration();
            assertEquals(expected, actual);
        }
    }
}
