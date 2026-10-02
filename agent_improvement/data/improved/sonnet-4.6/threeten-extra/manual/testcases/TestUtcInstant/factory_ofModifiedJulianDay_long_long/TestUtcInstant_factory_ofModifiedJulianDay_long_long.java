package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long {

    // Range covers negative, zero, and positive MJD values on non-leap days
    private static final long MJD_RANGE_START = -2;
    private static final long MJD_RANGE_END = 2;

    // Number of nanosecond-of-day sample values per day (small, valid, non-leap values)
    private static final int NANO_SAMPLE_COUNT = 10;

    @Test
    public void factory_ofModifiedJulianDay_long_long() {
        for (long mjDay = MJD_RANGE_START; mjDay <= MJD_RANGE_END; mjDay++) {
            for (int nanoOfDay = 0; nanoOfDay < NANO_SAMPLE_COUNT; nanoOfDay++) {
                UtcInstant instant = UtcInstant.ofModifiedJulianDay(mjDay, nanoOfDay);

                assertEquals(mjDay, instant.getModifiedJulianDay());
                assertEquals(nanoOfDay, instant.getNanoOfDay());
                assertFalse(instant.isLeapSecond());
            }
        }
    }
}
