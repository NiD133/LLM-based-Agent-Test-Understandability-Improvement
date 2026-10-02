package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_nanosNegative {

    // MJD 41683 corresponds to 1973-01-01
    private static final long MJD_1973_01_01 = 41683;

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosNegative() {
        // nanoOfDay must be non-negative; -1 should be rejected with DateTimeException
        assertThrows(DateTimeException.class, () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, -1));
    }
}
