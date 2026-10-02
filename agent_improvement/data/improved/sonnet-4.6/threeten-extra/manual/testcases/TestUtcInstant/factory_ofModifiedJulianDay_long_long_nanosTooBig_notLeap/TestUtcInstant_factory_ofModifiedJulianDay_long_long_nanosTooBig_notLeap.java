package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_nanosTooBig_notLeap {

    // MJD for 1973-01-01, a regular (non-leap-second) day
    private static final long MJD_1973_01_01 = 41683;

    // Maximum valid nanoOfDay for a standard 86400-second day
    private static final long NANOS_PER_DAY = 24L * 60 * 60 * 1_000_000_000L;

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosTooBig_notLeap() {
        // 1973-01-01 has no leap second, so nanoOfDay must be < NANOS_PER_DAY.
        // Passing exactly NANOS_PER_DAY should be rejected.
        assertThrows(DateTimeException.class,
                () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, NANOS_PER_DAY));
    }
}
