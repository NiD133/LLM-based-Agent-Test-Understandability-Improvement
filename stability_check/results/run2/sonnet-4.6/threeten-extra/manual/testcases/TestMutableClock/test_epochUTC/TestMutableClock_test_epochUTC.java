package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_epochUTC {

    /**
     * epochUTC() should initialise the clock at the Unix epoch (1970-01-01T00:00:00Z)
     * and pin it to the UTC time-zone.
     */
    @Test
    public void test_epochUTC() {
        MutableClock clock = MutableClock.epochUTC();

        assertEquals(Instant.EPOCH, clock.instant(),
                "epochUTC() clock should start at the Unix epoch");
        assertEquals(ZoneOffset.UTC, clock.getZone(),
                "epochUTC() clock should use the UTC time-zone");
    }
}
