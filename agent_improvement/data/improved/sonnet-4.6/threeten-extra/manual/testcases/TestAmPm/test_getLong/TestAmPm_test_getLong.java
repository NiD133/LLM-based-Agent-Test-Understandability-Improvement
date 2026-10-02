package org.threeten.extra;

import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAmPm_test_getLong {

    // AMPM_OF_DAY field values: AM = 0, PM = 1 (as defined by java.util.Calendar)
    private static final long AM_VALUE = 0L;
    private static final long PM_VALUE = 1L;

    @Test
    @DisplayName("getLong(AMPM_OF_DAY) returns 0 for AM")
    public void test_getLong_AM() {
        assertEquals(AM_VALUE, AmPm.AM.getLong(AMPM_OF_DAY));
    }

    @Test
    @DisplayName("getLong(AMPM_OF_DAY) returns 1 for PM")
    public void test_getLong_PM() {
        assertEquals(PM_VALUE, AmPm.PM.getLong(AMPM_OF_DAY));
    }
}
