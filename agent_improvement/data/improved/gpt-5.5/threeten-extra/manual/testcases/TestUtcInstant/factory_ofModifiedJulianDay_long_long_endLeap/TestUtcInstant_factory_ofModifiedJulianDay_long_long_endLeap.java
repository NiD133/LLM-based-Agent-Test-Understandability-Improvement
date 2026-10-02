package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endLeap {

    private static final long MJD_1972_12_31_LEAP = 41682L;
    private static final long SECONDS_PER_DAY = 24L * 60L * 60L;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long NANOS_PER_LEAP_DAY = (SECONDS_PER_DAY + 1L) * NANOS_PER_SECOND;

    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(
                MJD_1972_12_31_LEAP,
                NANOS_PER_LEAP_DAY - 1);

        assertEquals(MJD_1972_12_31_LEAP, instant.getModifiedJulianDay());
        assertEquals(NANOS_PER_LEAP_DAY - 1, instant.getNanoOfDay());
        assertTrue(instant.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", instant.toString());
    }
}
