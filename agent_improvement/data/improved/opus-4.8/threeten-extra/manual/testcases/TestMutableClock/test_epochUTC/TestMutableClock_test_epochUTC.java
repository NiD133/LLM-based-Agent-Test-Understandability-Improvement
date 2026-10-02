package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link MutableClock#epochUTC()}.
 */
public class TestMutableClock_test_epochUTC {

    /**
     * A clock created via {@code epochUTC()} should start at the Java epoch
     * (1970-01-01T00:00:00Z) and use the UTC time-zone.
     */
    @Test
    public void epochUTC_startsAtEpochInstantInUtcZone() {
        MutableClock clock = MutableClock.epochUTC();

        assertEquals(Instant.EPOCH, clock.instant(), "clock should start at the epoch instant");
        assertEquals(ZoneOffset.UTC, clock.getZone(), "clock should use the UTC time-zone");
    }
}
