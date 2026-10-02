package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_nanosTooBig_notLeap {

    private static final long MJD_1973_01_01 = 41683L;
    private static final long SECS_PER_DAY = 24L * 60L * 60L;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_notLeap() {
        assertThrows(DateTimeException.class,
                () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, NANOS_PER_DAY));
    }
}
