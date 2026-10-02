package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#setInstant(Instant)} overrides the clock's
 * current instant with whatever value is supplied.
 */
public class TestMutableClock_test_setInstant {

    @Test
    public void setInstant_replacesTheCurrentInstant() {
        // A clock created via epochUTC() starts at the epoch instant.
        MutableClock clock = MutableClock.epochUTC();
        assertEquals(Instant.EPOCH, clock.instant());

        // Setting the smallest possible instant takes effect immediately.
        clock.setInstant(Instant.MIN);
        assertEquals(Instant.MIN, clock.instant());

        // Setting the largest possible instant takes effect immediately.
        clock.setInstant(Instant.MAX);
        assertEquals(Instant.MAX, clock.instant());

        // Setting an ordinary instant (10 seconds after the epoch) also works.
        Instant tenSecondsAfterEpoch = Instant.EPOCH.plusSeconds(10);
        clock.setInstant(tenSecondsAfterEpoch);
        assertEquals(tenSecondsAfterEpoch, clock.instant());
    }
}
