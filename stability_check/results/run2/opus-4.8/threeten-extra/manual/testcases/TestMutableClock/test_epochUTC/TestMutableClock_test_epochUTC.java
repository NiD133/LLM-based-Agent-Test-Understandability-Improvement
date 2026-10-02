package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link MutableClock#epochUTC()} factory method.
 */
public class TestMutableClock_test_epochUTC {

    /**
     * A clock created via {@code epochUTC()} should start at the epoch instant
     * (1970-01-01T00:00:00Z) and use the UTC time-zone.
     */
    @Test
    public void test_epochUTC() {
        MutableClock clock = MutableClock.epochUTC();

        assertEquals(Instant.EPOCH, clock.instant(), "initial instant should be the epoch");
        assertEquals(ZoneOffset.UTC, clock.getZone(), "time-zone should be UTC");
    }
}
