package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_nanosTooBig_leap {

    // MJD for 1972-12-31, which is a leap-second day (has 86401 seconds)
    private static final long MJD_1972_12_31_LEAP = 41682;

    private static final long SECS_PER_DAY = 24L * 60 * 60;

    private static final long NANOS_PER_SEC = 1_000_000_000L;

    // The total nanoseconds in a leap day (86401 seconds), which is one past the valid maximum
    private static final long NANOS_PER_LEAP_DAY = (SECS_PER_DAY + 1) * NANOS_PER_SEC;

    // Passing nanoOfDay == NANOS_PER_LEAP_DAY is out of range even on a leap day,
    // because valid values are [0, NANOS_PER_LEAP_DAY - 1].
    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_leap() {
        assertThrows(DateTimeException.class, () -> UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_LEAP_DAY));
    }
}
