package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#toString()} reflects the clock's current
 * instant and time-zone, both after the instant is advanced and after the
 * time-zone is changed via {@link MutableClock#withZone}.
 */
public class TestMutableClock_test_toString {

    @Test
    public void test_toString() {
        // A fresh clock reports the epoch instant in the UTC zone.
        MutableClock clock = MutableClock.epochUTC();
        assertEquals("MutableClock[1970-01-01T00:00:00Z,Z]", clock.toString());

        // Advancing the instant by 30 years is reflected in the string.
        clock.add(Period.ofYears(30));
        assertEquals("MutableClock[2000-01-01T00:00:00Z,Z]", clock.toString());

        // A zone-changing view keeps the shared instant but shows the new zone.
        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);
        assertEquals("MutableClock[2000-01-01T00:00:00Z,-18:00]", withOtherZone.toString());
    }
}
