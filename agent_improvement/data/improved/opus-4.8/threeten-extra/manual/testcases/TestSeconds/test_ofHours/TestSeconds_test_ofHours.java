package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Seconds#ofHours(int)}.
 * <p>
 * {@code ofHours(h)} converts a number of hours into the equivalent number of
 * seconds, where one hour equals 3600 seconds.
 */
public class TestSeconds_test_ofHours {

    /** The number of seconds in one hour, used to derive the expected results. */
    private static final int SECONDS_PER_HOUR = 3600;

    @Test
    public void ofHours_convertsHoursToSeconds() {
        // Zero hours converts to zero seconds.
        assertEquals(0, Seconds.ofHours(0).getAmount());

        // Positive hours are multiplied by 3600.
        assertEquals(SECONDS_PER_HOUR, Seconds.ofHours(1).getAmount());
        assertEquals(2 * SECONDS_PER_HOUR, Seconds.ofHours(2).getAmount());

        // Negative hours are also multiplied by 3600, keeping the sign.
        assertEquals(-SECONDS_PER_HOUR, Seconds.ofHours(-1).getAmount());
        assertEquals(-2 * SECONDS_PER_HOUR, Seconds.ofHours(-2).getAmount());

        // The largest hour count that does not overflow when scaled to seconds.
        int maxHours = Integer.MAX_VALUE / SECONDS_PER_HOUR;
        assertEquals(maxHours * SECONDS_PER_HOUR, Seconds.ofHours(maxHours).getAmount());

        // The smallest (most negative) hour count that does not overflow.
        int minHours = Integer.MIN_VALUE / SECONDS_PER_HOUR;
        assertEquals(minHours * SECONDS_PER_HOUR, Seconds.ofHours(minHours).getAmount());
    }
}
