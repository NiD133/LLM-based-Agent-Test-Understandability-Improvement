package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_ofHour_int_singleton {

    private static final int FIRST_HOUR_OF_DAY = 0;
    private static final int FIRST_PM_HOUR = 12;
    private static final int HOURS_PER_DAY = 24;

    @Test
    public void test_ofHour_int_singleton() {
        for (int hour = FIRST_HOUR_OF_DAY; hour < FIRST_PM_HOUR; hour++) {
            assertSame(AmPm.AM, AmPm.ofHour(hour));
        }
        for (int hour = FIRST_PM_HOUR; hour < HOURS_PER_DAY; hour++) {
            assertSame(AmPm.PM, AmPm.ofHour(hour));
        }
    }
}
