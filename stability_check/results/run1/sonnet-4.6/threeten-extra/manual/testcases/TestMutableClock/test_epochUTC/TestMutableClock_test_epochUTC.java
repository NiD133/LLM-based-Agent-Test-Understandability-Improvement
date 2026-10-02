package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_epochUTC {

    /**
     * Verifies that a clock created via epochUTC() starts at the Unix epoch
     * (1970-01-01T00:00:00Z) and uses the UTC time-zone.
     */
    @Test
    public void test_epochUTC() {
        MutableClock clock = MutableClock.epochUTC();

        assertEquals(Instant.EPOCH, clock.instant(),
                "Clock instant should be the Unix epoch (1970-01-01T00:00:00Z)");
        assertEquals(ZoneOffset.UTC, clock.getZone(),
                "Clock zone should be UTC");
    }
}
