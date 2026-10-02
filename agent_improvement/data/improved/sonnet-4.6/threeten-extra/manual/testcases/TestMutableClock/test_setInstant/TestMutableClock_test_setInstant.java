package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_setInstant {

    @Test
    public void test_setInstant() {
        MutableClock clock = MutableClock.epochUTC();

        // Initial state: clock starts at the Unix epoch
        assertEquals(Instant.EPOCH, clock.instant());

        // Set to the earliest representable instant
        clock.setInstant(Instant.MIN);
        assertEquals(Instant.MIN, clock.instant());

        // Set to the latest representable instant
        clock.setInstant(Instant.MAX);
        assertEquals(Instant.MAX, clock.instant());

        // Set to an arbitrary instant after the epoch
        Instant tenSecondsAfterEpoch = Instant.EPOCH.plusSeconds(10);
        clock.setInstant(tenSecondsAfterEpoch);
        assertEquals(tenSecondsAfterEpoch, clock.instant());
    }
}
