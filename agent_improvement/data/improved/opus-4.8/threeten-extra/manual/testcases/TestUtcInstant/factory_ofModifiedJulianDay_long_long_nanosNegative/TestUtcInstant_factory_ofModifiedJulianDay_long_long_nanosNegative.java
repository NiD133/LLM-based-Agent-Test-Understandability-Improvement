package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#ofModifiedJulianDay(long, long)} rejects a
 * negative nano-of-day value.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long_nanosNegative {

    /** Modified Julian Day for 1973-01-01, an arbitrary valid date. */
    private static final long MJD_1973_01_01 = 41683;

    /** The nano-of-day must always be positive, so -1 is out of range. */
    private static final long NEGATIVE_NANO_OF_DAY = -1;

    @Test
    public void factory_ofModifiedJulianDay_long_long_nanosNegative() {
        assertThrows(DateTimeException.class,
                () -> UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, NEGATIVE_NANO_OF_DAY));
    }
}
