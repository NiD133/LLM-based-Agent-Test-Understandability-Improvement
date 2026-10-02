package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_durationUntil_oneDayLeap {

    // 1972-12-31 carries a leap second, so this Modified Julian Day spans 86401 seconds.
    private static final long MJD_1972_12_31_LEAP = 41682;

    private static final long MJD_1973_01_01 = 41683;

    private static final long SECONDS_IN_LEAP_DAY = 86401;

    @Test
    public void test_durationUntil_oneDayLeap() {
        UtcInstant startOfLeapDay = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        UtcInstant startOfNextDay = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);

        Duration elapsed = startOfLeapDay.durationUntil(startOfNextDay);

        assertEquals(SECONDS_IN_LEAP_DAY, elapsed.getSeconds());
        assertEquals(0, elapsed.getNano());
    }
}
