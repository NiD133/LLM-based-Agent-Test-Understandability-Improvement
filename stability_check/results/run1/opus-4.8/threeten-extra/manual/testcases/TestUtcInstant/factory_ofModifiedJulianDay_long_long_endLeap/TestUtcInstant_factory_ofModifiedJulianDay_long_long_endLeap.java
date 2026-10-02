package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link UtcInstant#ofModifiedJulianDay(long, long)} accepts a
 * nanosecond-of-day that falls within the extra leap second at the end of a leap day.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_endLeap {

    // Modified Julian Day for 1972-12-31, a day that ends with a positive leap second.
    private static final long MJD_1972_12_31_LEAP = 41682;

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;

    // A leap day is one second (i.e. one extra 23:59:60 second) longer than a normal day.
    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    @Test
    public void factory_ofModifiedJulianDay_long_long_endLeap() {
        // The last representable nanosecond of the leap day: 23:59:60.999999999
        long lastNanoOfLeapDay = NANOS_PER_LEAP_DAY - 1;

        UtcInstant instant = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, lastNanoOfLeapDay);

        assertEquals(MJD_1972_12_31_LEAP, instant.getModifiedJulianDay());
        assertEquals(lastNanoOfLeapDay, instant.getNanoOfDay());
        assertTrue(instant.isLeapSecond());
        assertEquals("1972-12-31T23:59:60.999999999Z", instant.toString());
    }
}
