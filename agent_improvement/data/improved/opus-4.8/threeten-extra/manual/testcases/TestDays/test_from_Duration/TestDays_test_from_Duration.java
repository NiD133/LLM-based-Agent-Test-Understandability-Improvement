package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#from(java.time.temporal.TemporalAmount)} when given a {@link Duration}.
 */
public class TestDays_test_from_Duration {

    @Test
    public void from_durationOfTwoDays_returnsDaysOfTwo() {
        Duration twoDays = Duration.ofDays(2);

        Days result = Days.from(twoDays);

        assertEquals(Days.of(2), result);
    }
}
