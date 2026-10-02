package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_nanosTooBig_leap {

    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long SECONDS_PER_STANDARD_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long NANOS_PER_1972_12_31_LEAP_DAY = (SECONDS_PER_STANDARD_DAY + 1) * NANOS_PER_SECOND;

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_leap() {
        assertThrows(
                DateTimeException.class,
                () -> UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_1972_12_31_LEAP_DAY));
    }
}
