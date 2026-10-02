package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endNormal {

    private static final long MJD_1972_12_31_LEAP = 41682L;
    private static final long SECONDS_PER_DAY = 24L * 60L * 60L;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECONDS_PER_DAY * NANOS_PER_SECOND;
    private static final long LAST_NANO_BEFORE_LEAP_SECOND = NANOS_PER_DAY - 1L;

    @Test
    public void factory_ofModifiedJulianDay_long_long_endNormal() {
        UtcInstant instant = UtcInstant.ofModifiedJulianDay(
                MJD_1972_12_31_LEAP,
                LAST_NANO_BEFORE_LEAP_SECOND);

        assertEquals(MJD_1972_12_31_LEAP, instant.getModifiedJulianDay());
        assertEquals(LAST_NANO_BEFORE_LEAP_SECOND, instant.getNanoOfDay());
        assertFalse(instant.isLeapSecond());
        assertEquals("1972-12-31T23:59:59.999999999Z", instant.toString());
    }
}
