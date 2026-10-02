package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#hashCode()} depends only on the shared
 * instant holder and the time-zone, so clocks that share updates and end up in
 * the same zone produce the same hash code.
 */
public class TestMutableClock_test_hashCode_sameWhenSharedUpdates {

    @Test
    public void test_hashCode_sameWhenSharedUpdates() {
        // The original clock uses the UTC zone.
        MutableClock utcClock = MutableClock.epochUTC();

        // withZone keeps the shared instant holder; switching to a different
        // zone and back to UTC yields a clock with the same holder and zone.
        MutableClock movedToMinZone = utcClock.withZone(ZoneOffset.MIN);
        MutableClock backToUtcZone = movedToMinZone.withZone(ZoneOffset.UTC);

        // Same shared holder + same zone => same hash code.
        assertEquals(utcClock.hashCode(), backToUtcZone.hashCode());
    }
}
