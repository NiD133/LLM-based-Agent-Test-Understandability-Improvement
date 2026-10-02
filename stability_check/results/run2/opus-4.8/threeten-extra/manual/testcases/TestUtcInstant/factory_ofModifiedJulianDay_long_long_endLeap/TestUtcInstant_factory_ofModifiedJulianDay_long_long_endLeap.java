package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UtcInstant#ofModifiedJulianDay(long, long)} for the moment that
 * lands inside the leap second added at the end of 1972-12-31.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endLeap {

    /** Modified Julian Day for 1972-12-31, a day that carries a positive leap second. */
    private static final long MJD_1972_12_31_LEAP = 41682;

    /** Number of seconds in a normal (non-leap) day. */
    private static final long SECS_PER_DAY = 24L * 60 * 60;

    /** Number of nanoseconds in one second. */
    private static final long NANOS_PER_SEC = 1_000_000_000L;

    /**
     * Nanoseconds in a leap-second day: one extra second beyond a normal day.
     * The last valid nanosecond-of-day is therefore {@code NANOS_PER_LEAP_DAY - 1}.
     */
    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        // Build the very last instant of the leap-second day (23:59:60.999999999).
        long lastNanoOfLeapDay = NANOS_PER_LEAP_DAY - 1;
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, lastNanoOfLeapDay);

        assertEquals(MJD_1972_12_31_LEAP, instant.getModifiedJulianDay());
        assertEquals(lastNanoOfLeapDay, instant.getNanoOfDay());
        assertTrue(instant.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", instant.toString());
    }
}
