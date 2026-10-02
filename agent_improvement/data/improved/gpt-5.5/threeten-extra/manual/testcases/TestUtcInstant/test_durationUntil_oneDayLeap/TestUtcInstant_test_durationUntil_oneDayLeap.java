package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_durationUntil_oneDayLeap {

    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01 = 41683;

    @Test
    public void test_durationUntil_oneDayLeap() {
        UtcInstant startOfLeapDay = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        UtcInstant startOfFollowingDay = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);

        Duration durationAcrossLeapDay = startOfLeapDay.durationUntil(startOfFollowingDay);

        assertEquals(86401, durationAcrossLeapDay.getSeconds());
        assertEquals(0, durationAcrossLeapDay.getNano());
    }
}
