package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endLeap {

    private static final long MODIFIED_JULIAN_DAY_1972_12_31_LEAP_SECOND = 41682;
    private static final long SECONDS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long NANOS_PER_LEAP_DAY = (SECONDS_PER_DAY + 1) * NANOS_PER_SECOND;

    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        long finalNanoOnLeapSecondDay = NANOS_PER_LEAP_DAY - 1;

        UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(
                MODIFIED_JULIAN_DAY_1972_12_31_LEAP_SECOND,
                finalNanoOnLeapSecondDay);

        assertEquals(MODIFIED_JULIAN_DAY_1972_12_31_LEAP_SECOND, utcInstant.getModifiedJulianDay());
        assertEquals(finalNanoOnLeapSecondDay, utcInstant.getNanoOfDay());
        assertTrue(utcInstant.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", utcInstant.toString());
    }
}
