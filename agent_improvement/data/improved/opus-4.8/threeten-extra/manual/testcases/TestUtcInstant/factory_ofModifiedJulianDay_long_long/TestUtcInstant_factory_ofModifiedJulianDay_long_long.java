package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UtcInstant#ofModifiedJulianDay(long, long)}.
 */
public class TestUtcInstant_factory_ofModifiedJulianDay_long_long {

    /**
     * For a range of ordinary (non-leap) Modified Julian Days and small
     * nano-of-day values, the factory must preserve both inputs exactly and
     * never flag the result as a leap second.
     */
    @Test
    public void factory_ofModifiedJulianDay_long_long() {
        for (long modifiedJulianDay = -2; modifiedJulianDay <= 2; modifiedJulianDay++) {
            for (int nanoOfDay = 0; nanoOfDay < 10; nanoOfDay++) {
                UtcInstant instant = UtcInstant.ofModifiedJulianDay(modifiedJulianDay, nanoOfDay);

                assertEquals(modifiedJulianDay, instant.getModifiedJulianDay());
                assertEquals(nanoOfDay, instant.getNanoOfDay());
                assertFalse(instant.isLeapSecond());
            }
        }
    }
}
