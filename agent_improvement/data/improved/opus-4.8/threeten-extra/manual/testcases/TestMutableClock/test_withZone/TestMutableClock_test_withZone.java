package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link MutableClock#withZone(java.time.ZoneId)}.
 */
public class TestMutableClock_test_withZone {

    @Test
    public void test_withZone() {
        // A clock created with epochUTC() starts at the epoch in the UTC zone.
        MutableClock utcClock = MutableClock.epochUTC();

        // withZone(...) returns a view that shares this clock's mutable instant,
        // so updates to one are visible through the other.
        MutableClock viewInMinZone = utcClock.withZone(ZoneOffset.MIN);

        // Switching the view back to UTC produces another shared view that, this
        // time, also matches the original clock's zone.
        MutableClock viewBackInUtcZone = viewInMinZone.withZone(ZoneOffset.UTC);

        // Update the instant through the original clock.
        utcClock.setInstant(Instant.MIN);

        // The shared instant is visible through every view.
        assertEquals(Instant.MIN, viewInMinZone.instant());
        assertEquals(Instant.MIN, viewBackInUtcZone.instant());

        // Each view keeps the zone it was created with.
        assertEquals(ZoneOffset.MIN, viewInMinZone.getZone());
        assertEquals(ZoneOffset.UTC, viewBackInUtcZone.getZone());

        // Two clocks are equal only when they share updates AND use the same zone.
        // viewInMinZone shares updates but uses a different zone, so it is not equal.
        assertNotEquals(utcClock, viewInMinZone);
        // viewBackInUtcZone shares updates and uses the same UTC zone, so it is equal.
        assertEquals(utcClock, viewBackInUtcZone);
    }
}
