package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_nanosNegative {

    private static final long MJD_1973_01_01 = 41683;

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosNegative() {
        assertThrows(DateTimeException.class, () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, -1));
    }
}
