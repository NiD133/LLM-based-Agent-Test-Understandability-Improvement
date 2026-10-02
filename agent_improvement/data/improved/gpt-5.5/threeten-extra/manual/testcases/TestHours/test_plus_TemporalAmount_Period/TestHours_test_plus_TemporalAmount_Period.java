package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_TemporalAmount_Period() {
        Hours fiveHours = Hours.of(5);

        assertEquals(Hours.of(5), fiveHours.plus(Duration.ofHours(0)));
        assertEquals(Hours.of(7), fiveHours.plus(Duration.ofHours(2)));
        assertEquals(Hours.of(3), fiveHours.plus(Duration.ofHours(-2)));
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE - 1).plus(Duration.ofHours(1)));
        assertEquals(Hours.of(Integer.MIN_VALUE), Hours.of(Integer.MIN_VALUE + 1).plus(Duration.ofHours(-1)));
    }
}
