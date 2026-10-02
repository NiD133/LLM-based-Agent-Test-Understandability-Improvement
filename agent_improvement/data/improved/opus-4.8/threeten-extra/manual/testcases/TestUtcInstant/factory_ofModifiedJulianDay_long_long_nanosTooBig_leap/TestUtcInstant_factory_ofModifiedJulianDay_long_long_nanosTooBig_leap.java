package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#ofModifiedJulianDay(long, long)} rejects a
 * nano-of-day value that exceeds the valid range, even on a leap-second day.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_nanosTooBig_leap {

    private static final long SECONDS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;

    /** Modified Julian Day for 1972-12-31, a day that contains a positive leap second. */
    private static final long MJD_1972_12_31_LEAP = 41682;

    /**
     * One nanosecond beyond the last valid nano-of-day on a leap-second day.
     * A leap day lasts {@code SECONDS_PER_DAY + 1} seconds, so its highest valid
     * nano-of-day is {@code (SECONDS_PER_DAY + 1) * NANOS_PER_SECOND - 1}; this
     * value is exactly one nanosecond too large.
     */
    private static final long NANOS_TOO_BIG_FOR_LEAP_DAY = (SECONDS_PER_DAY + 1) * NANOS_PER_SECOND;

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_leap() {
        assertThrows(DateTimeException.class,
                () -> UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_TOO_BIG_FOR_LEAP_DAY));
    }
}
