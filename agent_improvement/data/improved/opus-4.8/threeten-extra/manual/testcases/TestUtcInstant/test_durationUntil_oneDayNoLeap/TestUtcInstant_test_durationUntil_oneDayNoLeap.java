package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UtcInstant#durationUntil(UtcInstant)} across a normal day boundary.
 */
public class TestUtcInstant_test_durationUntil_oneDayNoLeap {

    // Modified Julian Day numbers for two consecutive midnights that do NOT
    // straddle a leap second, so the gap between them is exactly 24 hours.
    private static final long MJD_1972_12_30 = 41681;
    private static final long MJD_1972_12_31 = 41682;

    private static final long SECONDS_PER_NORMAL_DAY = 24L * 60 * 60; // 86400

    @Test
    public void test_durationUntil_oneDayNoLeap() {
        UtcInstant startOfDec30 = UtcInstant.ofModifiedJulianDay(MJD_1972_12_30, 0);
        UtcInstant startOfDec31 = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31, 0);

        Duration oneDay = startOfDec30.durationUntil(startOfDec31);

        assertEquals(SECONDS_PER_NORMAL_DAY, oneDay.getSeconds());
        assertEquals(0, oneDay.getNano());
    }
}
