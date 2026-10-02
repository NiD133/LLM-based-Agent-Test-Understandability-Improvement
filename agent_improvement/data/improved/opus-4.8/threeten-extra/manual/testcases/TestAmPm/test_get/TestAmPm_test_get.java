package org.threeten.extra;

import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link AmPm#get(java.time.temporal.TemporalField)} for the AMPM_OF_DAY field.
 */
public class TestAmPm_test_get {

    @Test
    public void get_amPmOfDay_returnsZeroForAmAndOneForPm() {
        assertEquals(0, AmPm.AM.get(AMPM_OF_DAY), "AM should map to 0");
        assertEquals(1, AmPm.PM.get(AMPM_OF_DAY), "PM should map to 1");
    }
}
