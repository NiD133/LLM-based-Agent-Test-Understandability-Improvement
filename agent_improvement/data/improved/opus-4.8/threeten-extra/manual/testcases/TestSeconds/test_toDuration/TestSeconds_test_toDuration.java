package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#toDuration()}.
 */
public class TestSeconds_test_toDuration {

    /**
     * {@code toDuration()} must yield a {@link Duration} holding the same number
     * of seconds as the {@code Seconds} amount, for both negative and positive values.
     */
    @Test
    public void test_toDuration() {
        for (int seconds = -20; seconds < 20; seconds++) {
            Duration expected = Duration.ofSeconds(seconds);
            Duration actual = Seconds.of(seconds).toDuration();
            assertEquals(expected, actual);
        }
    }
}
