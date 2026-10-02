package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_ofModifiedJulianDay_long_long {

    private static final long FIRST_MODIFIED_JULIAN_DAY = -2;
    private static final long LAST_MODIFIED_JULIAN_DAY = 2;
    private static final int FIRST_NANO_OF_DAY = 0;
    private static final int LAST_NANO_OF_DAY = 9;

    @Test
    public void factory_ofModifiedJulianDay_long_long() {
        for (long modifiedJulianDay = FIRST_MODIFIED_JULIAN_DAY;
                modifiedJulianDay <= LAST_MODIFIED_JULIAN_DAY;
                modifiedJulianDay++) {
            assertFactoryPreservesValidNonLeapSecondInstants(modifiedJulianDay);
        }
    }

    private void assertFactoryPreservesValidNonLeapSecondInstants(long modifiedJulianDay) {
        for (int nanoOfDay = FIRST_NANO_OF_DAY; nanoOfDay <= LAST_NANO_OF_DAY; nanoOfDay++) {
            UtcInstant instant = UtcInstant.ofModifiedJulianDay(modifiedJulianDay, nanoOfDay);

            assertEquals(modifiedJulianDay, instant.getModifiedJulianDay());
            assertEquals(nanoOfDay, instant.getNanoOfDay());
            assertFalse(instant.isLeapSecond());
        }
    }
}
