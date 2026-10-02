package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;
import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_durationUntil_oneDayLeapNegative {

    // MJD values for the two instants that straddle the 1972-12-31 leap second
    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long MJD_1973_01_01      = 41683;

    // A regular day has 86400 seconds; 1972-12-31 had an extra leap second (86401 s)
    private static final long SECS_PER_LEAP_DAY = 86401L;

    @Test
    public void test_durationUntil_oneDayLeapNegative() {
        // Compute the duration looking *backward* from 1973-01-01 to 1972-12-31
        UtcInstant start = UtcInstant.ofModifiedJulianDay(MJD_1973_01_01, 0);
        UtcInstant end   = UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, 0);

        Duration duration = start.durationUntil(end);

        // The duration is negative because end precedes start.
        // Its magnitude is 86401 s (not 86400) because 1972-12-31 carried a positive leap second.
        assertEquals(-SECS_PER_LEAP_DAY, duration.getSeconds());
        assertEquals(0, duration.getNano());
    }
}
