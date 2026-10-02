package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UtcInstant#ofModifiedJulianDay(long, long)} at the very last
 * nanosecond of a leap-second day, just before the leap second begins.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endNormal {

    /** Modified Julian Day for 1972-12-31, a day that ends with a leap second. */
    private static final long MJD_1972_12_31_LEAP = 41682;

    /** Number of nanoseconds in a normal (non-leap) 86,400-second day. */
    private static final long NANOS_PER_DAY = 24L * 60 * 60 * 1_000_000_000L;

    @Test
    public void factory_ofModifiedJulianDay_long_long_endNormal() {
        // The last nanosecond of the regular part of the day (23:59:59.999999999),
        // one nanosecond before the leap second at 23:59:60 would start.
        long lastNanoBeforeLeapSecond = NANOS_PER_DAY - 1;

        UtcInstant instant =
                UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, lastNanoBeforeLeapSecond);

        assertEquals(MJD_1972_12_31_LEAP, instant.getModifiedJulianDay());
        assertEquals(lastNanoBeforeLeapSecond, instant.getNanoOfDay());
        assertFalse(instant.isLeapSecond());
        assertEquals("1972-12-31T23:59:59.999999999Z", instant.toString());
    }
}
