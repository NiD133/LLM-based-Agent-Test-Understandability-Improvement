package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AmPm#ofHour(int)} maps every valid hour-of-day to the
 * correct shared {@code AmPm} singleton.
 */
public class TestAmPm_test_ofHour_int_singleton {

    /** Hours 0-11 belong to the morning; 12-23 belong to the afternoon. */
    private static final int FIRST_PM_HOUR = 12;
    private static final int HOURS_PER_DAY = 24;

    //-----------------------------------------------------------------------
    @Test
    public void test_ofHour_int_singleton() {
        // Morning hours (00:00-11:59) must resolve to the AM singleton.
        for (int hourOfDay = 0; hourOfDay < FIRST_PM_HOUR; hourOfDay++) {
            assertSame(AmPm.AM, AmPm.ofHour(hourOfDay));
        }
        // Afternoon hours (12:00-23:59) must resolve to the PM singleton.
        for (int hourOfDay = FIRST_PM_HOUR; hourOfDay < HOURS_PER_DAY; hourOfDay++) {
            assertSame(AmPm.PM, AmPm.ofHour(hourOfDay));
        }
    }
}
