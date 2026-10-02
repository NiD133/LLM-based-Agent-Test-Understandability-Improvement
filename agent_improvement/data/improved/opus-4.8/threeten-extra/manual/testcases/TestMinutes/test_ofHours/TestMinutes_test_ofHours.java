package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#ofHours(int)}, which converts a number of hours
 * into the equivalent number of minutes (hours * 60).
 */
public class TestMinutes_test_ofHours {

    private static final int MINUTES_PER_HOUR = 60;

    @Test
    public void ofHours_convertsHoursToMinutes() {
        // Zero stays zero.
        assertEquals(0, Minutes.ofHours(0).getAmount());

        // Positive hours are multiplied by 60.
        assertEquals(60, Minutes.ofHours(1).getAmount());
        assertEquals(120, Minutes.ofHours(2).getAmount());

        // Negative hours are also multiplied by 60.
        assertEquals(-60, Minutes.ofHours(-1).getAmount());
        assertEquals(-120, Minutes.ofHours(-2).getAmount());

        // The largest hour value that converts to minutes without overflowing an int.
        int maxHoursWithoutOverflow = Integer.MAX_VALUE / MINUTES_PER_HOUR;
        assertEquals(maxHoursWithoutOverflow * MINUTES_PER_HOUR,
                Minutes.ofHours(maxHoursWithoutOverflow).getAmount());

        // The smallest (most negative) hour value that converts without overflowing an int.
        int minHoursWithoutOverflow = Integer.MIN_VALUE / MINUTES_PER_HOUR;
        assertEquals(minHoursWithoutOverflow * MINUTES_PER_HOUR,
                Minutes.ofHours(minHoursWithoutOverflow).getAmount());
    }
}
