package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#plus(java.time.temporal.TemporalAmount)}.
 * <p>
 * Each case adds a {@link Duration} (a {@code TemporalAmount}) to a base
 * {@code Hours} value and verifies the resulting hours.
 */
public class TestHours_test_plus_TemporalAmount_Period {

    @Test
    public void plus_temporalAmount_addsHoursOfDuration() {
        Hours fiveHours = Hours.of(5);

        // Adding a zero duration leaves the amount unchanged.
        assertEquals(Hours.of(5), fiveHours.plus(Duration.ofHours(0)));

        // Adding a positive duration increases the amount.
        assertEquals(Hours.of(7), fiveHours.plus(Duration.ofHours(2)));

        // Adding a negative duration decreases the amount.
        assertEquals(Hours.of(3), fiveHours.plus(Duration.ofHours(-2)));

        // Adding up to the int boundaries is allowed (no overflow).
        assertEquals(Hours.of(Integer.MAX_VALUE),
                Hours.of(Integer.MAX_VALUE - 1).plus(Duration.ofHours(1)));
        assertEquals(Hours.of(Integer.MIN_VALUE),
                Hours.of(Integer.MIN_VALUE + 1).plus(Duration.ofHours(-1)));
    }
}
