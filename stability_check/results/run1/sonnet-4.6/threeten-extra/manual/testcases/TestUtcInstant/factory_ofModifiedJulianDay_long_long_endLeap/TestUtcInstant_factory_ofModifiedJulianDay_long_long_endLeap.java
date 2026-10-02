package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endLeap {

    // MJD for 1972-12-31, the date of the first UTC leap second
    private static final long MJD_1972_12_31_LEAP = 41682;

    private static final long SECS_PER_DAY = 24L * 60 * 60;

    private static final long NANOS_PER_SEC = 1_000_000_000L;

    // A leap day has one extra second (86401 seconds total)
    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    /**
     * Verifies that ofModifiedJulianDay accepts the last nanosecond of the leap second
     * on 1972-12-31 (i.e., 23:59:60.999999999Z) and correctly reports it as a leap second.
     */
    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        // The last nanosecond within the leap second: 23:59:60.999999999Z
        UtcInstant t = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY - 1);

        assertEquals(MJD_1972_12_31_LEAP, t.getModifiedJulianDay());
        assertEquals(NANOS_PER_LEAP_DAY - 1, t.getNanoOfDay());
        assertTrue(t.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", t.toString());
    }
}
