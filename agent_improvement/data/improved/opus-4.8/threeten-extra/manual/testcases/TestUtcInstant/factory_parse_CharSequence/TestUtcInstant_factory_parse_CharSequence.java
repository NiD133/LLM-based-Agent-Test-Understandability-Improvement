package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UtcInstant#parse(CharSequence)}, focusing on how an ISO-8601 text
 * is decoded into a Modified Julian Day plus a nano-of-day, including the leap second.
 */
public class TestUtcInstant_factory_parse_CharSequence {

    /** Modified Julian Day for 1972-12-31, a day that ends with a leap second. */
    private static final long MJD_1972_12_31_LEAP = 41682;

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    /** Nanoseconds in a normal (non-leap) day; also the nano-of-day at which a leap second starts. */
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    @Test
    public void factory_parse_CharSequence() {
        // The final second of the leap day (23:59:59) maps to one second before end-of-day.
        assertEquals(
                UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY - NANOS_PER_SEC),
                UtcInstant.parse("1972-12-31T23:59:59Z"));
        // The leap second itself (23:59:60) maps to the extra nano-of-day beyond a normal day.
        assertEquals(
                UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY),
                UtcInstant.parse("1972-12-31T23:59:60Z"));
    }
}
