package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_startLeap {

    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long SECONDS_PER_DAY = 24L * 60L * 60L;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECONDS_PER_DAY * NANOS_PER_SECOND;

    @Test
    public void factory_ofModifiedJulianDay_long_long_startLeap() {
        UtcInstant leapSecond = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY);

        assertEquals(MJD_1972_12_31_LEAP, leapSecond.getModifiedJulianDay());
        assertEquals(NANOS_PER_DAY, leapSecond.getNanoOfDay());
        assertTrue(leapSecond.isLeapSecond());
        assertEquals("1972-12-31T23:59:60Z", leapSecond.toString());
    }
}
