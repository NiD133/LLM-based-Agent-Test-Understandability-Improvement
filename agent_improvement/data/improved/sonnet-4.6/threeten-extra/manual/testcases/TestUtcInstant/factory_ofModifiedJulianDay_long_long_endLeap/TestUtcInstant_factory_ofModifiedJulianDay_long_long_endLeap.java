package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endLeap {

    // MJD for 1972-12-31, which was a positive leap-second day (the day had 86401 seconds)
    private static final long MJD_1972_12_31_LEAP = 41682;

    private static final long SECS_PER_DAY = 24L * 60 * 60;

    private static final long NANOS_PER_SEC = 1_000_000_000L;

    // Total nanoseconds in a leap-second day: one extra second beyond the normal 86400
    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    /**
     * Verifies that the very last nanosecond of the 1972-12-31 leap second
     * (i.e. 23:59:60.999999999Z) is represented correctly.
     *
     * The nano-of-day value {@code NANOS_PER_LEAP_DAY - 1} falls inside the
     * extra leap second, so {@code isLeapSecond()} must return true and
     * {@code toString()} must display second-value 60.
     */
    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        long lastNanoOfLeapDay = NANOS_PER_LEAP_DAY - 1;

        UtcInstant t = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, lastNanoOfLeapDay);

        assertEquals(MJD_1972_12_31_LEAP, t.getModifiedJulianDay());
        assertEquals(lastNanoOfLeapDay, t.getNanoOfDay());
        assertTrue(t.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", t.toString());
    }
}
