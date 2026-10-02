package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#ofModifiedJulianDay(long, long)} rejects a
 * nano-of-day value that is too large for an ordinary (non-leap) day.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_nanosTooBig_notLeap {

    /** Modified Julian Day for 1973-01-01, a normal day with no leap second. */
    private static final long MJD_1973_01_01 = 41683;

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;

    /**
     * Number of nanoseconds in a normal day. On a non-leap day the valid
     * nano-of-day range is {@code [0, NANOS_PER_DAY)}, so this value itself is
     * one nanosecond past the end and must be rejected.
     */
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_notLeap() {
        // NANOS_PER_DAY is out of range for a non-leap day, so creation must fail.
        assertThrows(DateTimeException.class,
                () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, NANOS_PER_DAY));
    }
}
