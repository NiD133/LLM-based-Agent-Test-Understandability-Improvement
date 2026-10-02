package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_TemporalAmount_Period() {
        Hours base = Hours.of(5);

        // Adding zero hours leaves the value unchanged
        assertEquals(Hours.of(5), base.plus(Duration.ofHours(0)));

        // Adding a positive duration increases the hour count
        assertEquals(Hours.of(7), base.plus(Duration.ofHours(2)));

        // Adding a negative duration decreases the hour count
        assertEquals(Hours.of(3), base.plus(Duration.ofHours(-2)));

        // Adding near the positive overflow boundary stays within int range
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE - 1).plus(Duration.ofHours(1)));

        // Adding near the negative overflow boundary stays within int range
        assertEquals(Hours.of(Integer.MIN_VALUE), Hours.of(Integer.MIN_VALUE + 1).plus(Duration.ofHours(-1)));
    }
}
