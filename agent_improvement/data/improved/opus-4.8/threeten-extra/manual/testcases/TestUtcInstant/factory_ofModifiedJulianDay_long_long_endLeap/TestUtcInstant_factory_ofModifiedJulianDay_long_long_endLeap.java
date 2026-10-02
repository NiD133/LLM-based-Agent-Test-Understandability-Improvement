package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UtcInstant#ofModifiedJulianDay(long, long)} for the final nanosecond
 * of a day that ends with a leap second.
 *
 * <p>1972-12-31 was a leap-second day (Modified Julian Day 41682). Such a day runs for
 * one extra second, so it spans {@code (86400 + 1)} seconds instead of the usual 86400.
 * This test builds the instant at the very last nanosecond of that leap day and verifies
 * that the factory preserves the leap second rather than rolling over into the next day.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endLeap {

    /** Modified Julian Day for 1972-12-31, a day that ends with a leap second. */
    private static final long MJD_1972_12_31_LEAP = 41682;

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;

    /** Number of nanoseconds in a day that contains a trailing leap second (86401 seconds). */
    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    /** Nano-of-day of the last nanosecond of the leap day (one nanosecond before it ends). */
    private static final long LAST_NANO_OF_LEAP_DAY = NANOS_PER_LEAP_DAY - 1;

    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, LAST_NANO_OF_LEAP_DAY);

        assertEquals(MJD_1972_12_31_LEAP, instant.getModifiedJulianDay());
        assertEquals(LAST_NANO_OF_LEAP_DAY, instant.getNanoOfDay());
        assertTrue(instant.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", instant.toString());
    }
}
