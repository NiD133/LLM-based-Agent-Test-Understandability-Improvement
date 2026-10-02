package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#durationUntil} accounts for the leap second on
 * 1972-12-31, reporting a day length of 86401 seconds instead of the usual 86400.
 */
public class TestUtcInstant_test_durationUntil_oneDayLeap {

    // MJD 41682 = 1972-12-31, a day that contained a positive leap second (23:59:60)
    private static final long MJD_1972_12_31_LEAP = 41682;

    // MJD 41683 = 1973-01-01, the day immediately after the leap second
    private static final long MJD_1973_01_01 = 41683;

    @Test
    public void test_durationUntil_oneDayLeap() {
        // Start of the leap-second day (midnight at the beginning of 1972-12-31)
        UtcInstant startOfLeapDay = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        // Start of the following day (midnight at the beginning of 1973-01-01)
        UtcInstant startOfNextDay = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);

        Duration duration = startOfLeapDay.durationUntil(startOfNextDay);

        // A leap day spans 86401 seconds (86400 standard seconds + 1 leap second)
        assertEquals(86401, duration.getSeconds());
        assertEquals(0, duration.getNano());
    }
}
