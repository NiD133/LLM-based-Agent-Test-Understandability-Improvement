package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_durationUntil_oneDayNoLeap {

    private static final long MJD_1972_12_30 = 41681;
    private static final long MJD_1972_12_31_LEAP = 41682;

    @Test
    public void test_durationUntil_oneDayNoLeap() {
        UtcInstant startOfDayBeforeLeapDay = UtcInstant.ofModifiedJulianDay(MJD_1972_12_30, 0);
        UtcInstant startOfLeapDay = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);

        Duration test = startOfDayBeforeLeapDay.durationUntil(startOfLeapDay);

        assertEquals(86400, test.getSeconds());
        assertEquals(0, test.getNano());
    }
}
