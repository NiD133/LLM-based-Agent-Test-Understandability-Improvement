package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_durationUntil_oneDayLeapNegative {

    // Modified Julian Days for three consecutive dates around a leap second.
    // 1972-12-31 is a leap day: it has 86401 seconds (one extra leap second).
    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01 = 41683;

    @Test
    public void test_durationUntil_oneDayLeapNegative() {
        UtcInstant startOf1973 = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);
        UtcInstant startOfLeapDay = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);

        // Going backwards across the 86401-second leap day yields a negative duration.
        Duration elapsed = startOf1973.durationUntil(startOfLeapDay);

        assertEquals(-86401, elapsed.getSeconds());
        assertEquals(0, elapsed.getNano());
    }
}
