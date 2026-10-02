package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_withZone {

    /**
     * withZone() creates a new view of the clock that shares the same underlying
     * instant. Updating the original clock's instant is visible to all shared
     * views. Each view keeps its own zone, and two views with the same zone and
     * shared instant-holder compare as equal.
     */
    @Test
    public void test_withZone() {
        // Arrange: base clock at epoch in UTC; derive two zone-views that share its instant
        MutableClock baseClock = MutableClock.epochUTC();
        MutableClock minZoneView = baseClock.withZone(ZoneOffset.MIN);
        MutableClock utcZoneView = minZoneView.withZone(ZoneOffset.UTC);

        // Act: advance the base clock to Instant.MIN
        baseClock.setInstant(Instant.MIN);

        // Assert: all shared views reflect the updated instant
        assertEquals(Instant.MIN, minZoneView.instant());
        assertEquals(Instant.MIN, utcZoneView.instant());

        // Assert: each view retains the zone it was created with
        assertEquals(ZoneOffset.MIN, minZoneView.getZone());
        assertEquals(ZoneOffset.UTC, utcZoneView.getZone());

        // Assert: a view with a different zone is not equal to the base clock,
        // but a view sharing the same zone and instant-holder is equal
        assertNotEquals(baseClock, minZoneView);
        assertEquals(baseClock, utcZoneView);
    }
}
