package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#minus(java.time.temporal.TemporalAmount)} when the amount
 * to subtract is supplied as a {@link Duration}.
 */
public class TestHours_test_minus_TemporalAmount_Duration {

    @Test
    public void test_minus_TemporalAmount_Duration() {
        Hours five = Hours.of(5);

        // Subtracting a zero duration leaves the value unchanged.
        assertEquals(Hours.of(5), five.minus(Duration.ofHours(0)));

        // Subtracting a positive duration decreases the value.
        assertEquals(Hours.of(3), five.minus(Duration.ofHours(2)));

        // Subtracting a negative duration increases the value.
        assertEquals(Hours.of(7), five.minus(Duration.ofHours(-2)));

        // Subtracting a negative duration may reach Integer.MAX_VALUE without overflow.
        assertEquals(
                Hours.of(Integer.MAX_VALUE),
                Hours.of(Integer.MAX_VALUE - 1).minus(Duration.ofHours(-1)));

        // Subtracting a positive duration may reach Integer.MIN_VALUE without overflow.
        assertEquals(
                Hours.of(Integer.MIN_VALUE),
                Hours.of(Integer.MIN_VALUE + 1).minus(Duration.ofHours(1)));
    }
}
