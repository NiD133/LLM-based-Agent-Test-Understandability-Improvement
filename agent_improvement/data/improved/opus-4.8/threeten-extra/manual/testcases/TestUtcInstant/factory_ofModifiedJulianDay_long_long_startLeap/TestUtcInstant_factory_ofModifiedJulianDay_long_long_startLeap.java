package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#ofModifiedJulianDay(long, long)} correctly builds an
 * instant positioned exactly at the start of a leap second.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_startLeap {

    /** Modified Julian Day for 1972-12-31, a day that ends with a leap second. */
    private static final long MJD_1972_12_31_LEAP = 41682;

    /** Number of nanoseconds in a normal (non-leap) 24-hour day. */
    private static final long NANOS_PER_NORMAL_DAY = 24L * 60 * 60 * 1_000_000_000L;

    /**
     * A nano-of-day equal to a full normal day lands on the extra leap second
     * (23:59:60) of a leap day, so the resulting instant is a leap second.
     */
    @Test
    public void factory_ofModifiedJulianDay_long_long_startLeap() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_NORMAL_DAY);

        assertEquals(MJD_1972_12_31_LEAP, instant.getModifiedJulianDay());
        assertEquals(NANOS_PER_NORMAL_DAY, instant.getNanoOfDay());
        assertTrue(instant.isLeapSecond());
        assertEquals("1972-12-31T23:59:60Z", instant.toString());
    }
}
