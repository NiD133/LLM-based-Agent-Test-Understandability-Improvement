package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#ofModifiedJulianDay} correctly creates an instant
 * at the very last nanosecond of the leap second on 1972-12-31 — the first positive
 * leap second ever inserted into UTC.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endLeap {

    // MJD 41682 corresponds to 1972-12-31, a day that had a positive leap second
    // (i.e., the day lasted 86,401 seconds instead of the usual 86,400).
    private static final long MJD_1972_12_31_LEAP = 41682;

    private static final long SECS_PER_DAY       = 24L * 60 * 60;
    private static final long NANOS_PER_SEC       = 1_000_000_000L;

    // Total nanoseconds in a leap-second day: 86,401 seconds worth of nanos.
    private static final long NANOS_PER_LEAP_DAY  = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        // The last nanosecond of 1972-12-31 falls inside the leap second (23:59:60.999999999Z).
        UtcInstant t = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1);

        assertEquals(MJD_1972_12_31_LEAP,    t.getModifiedJulianDay());
        assertEquals(NANOS_PER_LEAP_DAY - 1, t.getNanoOfDay());
        assertTrue(t.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", t.toString());
    }
}
