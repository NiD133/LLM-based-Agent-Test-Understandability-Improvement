package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_durationUntil_oneDayNoLeap {

    // MJD values for 1972-12-30 and the start of the leap day 1972-12-31
    private static final long MJD_1972_12_30 = 41681;
    private static final long MJD_1972_12_31_LEAP = 41682;

    @Test
    public void test_durationUntil_oneDayNoLeap() {
        // Duration from the start of 1972-12-30 to the start of 1972-12-31 (a leap day)
        // spans exactly one non-leap day: 86400 seconds with no nanosecond remainder
        UtcInstant utc1 = UtcInstant.ofModifiedJulianDay(MJD_1972_12_30, 0);
        UtcInstant utc2 = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);
        Duration test = utc1.durationUntil(utc2);
        assertEquals(86400, test.getSeconds());
        assertEquals(0, test.getNano());
    }
}
