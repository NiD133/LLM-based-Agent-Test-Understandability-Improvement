package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_TemporalAmount_Duration {

    @Test
    public void test_minus_TemporalAmount_Duration() {
        Hours fiveHours = Hours.of(5);

        assertEquals(Hours.of(5), fiveHours.minus(Duration.ofHours(0)));
        assertEquals(Hours.of(3), fiveHours.minus(Duration.ofHours(2)));
        assertEquals(Hours.of(7), fiveHours.minus(Duration.ofHours(-2)));
        assertEquals(
                Hours.of(Integer.MAX_VALUE),
                Hours.of(Integer.MAX_VALUE - 1).minus(Duration.ofHours(-1)));
        assertEquals(
                Hours.of(Integer.MIN_VALUE),
                Hours.of(Integer.MIN_VALUE + 1).minus(Duration.ofHours(1)));
    }
}
