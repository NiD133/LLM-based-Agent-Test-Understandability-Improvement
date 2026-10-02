package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#toString()} renders the clock's current
 * instant and time-zone in the format {@code MutableClock[<instant>,<zone>]},
 * and that it reflects updates to the instant as well as a switch of time-zone.
 */
public class TestMutableClock_test_toString {

    @Test
    public void toString_reflectsInstantAndZone() {
        // A fresh clock starts at the epoch (1970-01-01T00:00:00Z) in the UTC zone.
        MutableClock clock = MutableClock.epochUTC();
        assertEquals(
                "MutableClock[1970-01-01T00:00:00Z,Z]",
                clock.toString());

        // Advancing the clock by 30 years is reflected in the instant shown.
        clock.add(Period.ofYears(30));
        assertEquals(
                "MutableClock[2000-01-01T00:00:00Z,Z]",
                clock.toString());

        // A view in a different zone keeps the same instant but shows the new offset.
        MutableClock clockInMinOffsetZone = clock.withZone(ZoneOffset.MIN);
        assertEquals(
                "MutableClock[2000-01-01T00:00:00Z,-18:00]",
                clockInMinOffsetZone.toString());
    }
}
