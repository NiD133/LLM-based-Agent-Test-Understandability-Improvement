package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Seconds#ofMinutes(int)}.
 * <p>
 * {@code ofMinutes(m)} is expected to return a {@code Seconds} whose amount is
 * {@code m * 60}.
 */
public class TestSeconds_test_ofMinutes {

    /** The number of seconds in one minute, used to derive the expected amounts. */
    private static final int SECONDS_PER_MINUTE = 60;

    @Test
    public void ofMinutes_convertsMinutesToSeconds() {
        // Zero minutes -> zero seconds.
        assertEquals(0, Seconds.ofMinutes(0).getAmount());

        // Small positive amounts are scaled by 60.
        assertEquals(60, Seconds.ofMinutes(1).getAmount());
        assertEquals(120, Seconds.ofMinutes(2).getAmount());

        // Largest minute value that still fits in an int once scaled by 60.
        int maxMinutes = Integer.MAX_VALUE / SECONDS_PER_MINUTE;
        assertEquals(maxMinutes * SECONDS_PER_MINUTE, Seconds.ofMinutes(maxMinutes).getAmount());

        // Small negative amounts are scaled by 60.
        assertEquals(-60, Seconds.ofMinutes(-1).getAmount());
        assertEquals(-120, Seconds.ofMinutes(-2).getAmount());

        // Smallest (most negative) minute value that still fits in an int once scaled by 60.
        int minMinutes = Integer.MIN_VALUE / SECONDS_PER_MINUTE;
        assertEquals(minMinutes * SECONDS_PER_MINUTE, Seconds.ofMinutes(minMinutes).getAmount());
    }
}
