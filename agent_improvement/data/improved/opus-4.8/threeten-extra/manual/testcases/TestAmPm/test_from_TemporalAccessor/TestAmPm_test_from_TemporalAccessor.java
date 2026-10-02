package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link AmPm#from(java.time.temporal.TemporalAccessor)}, which derives the
 * AM/PM half-day from any temporal that supports the AMPM_OF_DAY field.
 */
public class TestAmPm_test_from_TemporalAccessor {

    @Test
    public void from_morningTime_returnsAm() {
        LocalTime morning = LocalTime.of(8, 30);
        assertEquals(AmPm.AM, AmPm.from(morning));
    }

    @Test
    public void from_afternoonTime_returnsPm() {
        LocalTime afternoon = LocalTime.of(17, 30);
        assertEquals(AmPm.PM, AmPm.from(afternoon));
    }
}
