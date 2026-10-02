package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_durationUntil_oneDayLeapNegative {

    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01 = 41683;
    private static final long START_OF_DAY_NANOS = 0;
    private static final long NEGATIVE_LEAP_DAY_SECONDS = -86401;

    @Test
    public void test_durationUntil_oneDayLeapNegative() {
        UtcInstant startOfDayAfterLeapSecond = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, START_OF_DAY_NANOS);
        UtcInstant startOfLeapSecondDay = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, START_OF_DAY_NANOS);

        Duration duration = startOfDayAfterLeapSecond.durationUntil(startOfLeapSecondDay);

        assertEquals(NEGATIVE_LEAP_DAY_SECONDS, duration.getSeconds());
        assertEquals(0, duration.getNano());
    }
}
