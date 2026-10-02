package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#toString()} renders the clock's current
 * instant and time-zone in the documented {@code MutableClock[instant,zone]}
 * format, and that it reflects updates to the instant and changes to the zone.
 */
public class TestMutableClock_test_toString {

    @Test
    public void test_toString() {
        // A fresh clock starts at the epoch (1970-01-01T00:00:00Z) in UTC.
        MutableClock clock = MutableClock.epochUTC();
        assertEquals(
                "MutableClock[1970-01-01T00:00:00Z,Z]",
                clock.toString(),
                "toString should show the epoch instant and the UTC zone");

        // Advancing the instant is reflected in toString.
        clock.add(Period.ofYears(30));
        assertEquals(
                "MutableClock[2000-01-01T00:00:00Z,Z]",
                clock.toString(),
                "toString should show the advanced instant after adding 30 years");

        // A zone-shifted view shares the same instant but reports its own zone.
        MutableClock clockInMinOffset = clock.withZone(ZoneOffset.MIN);
        assertEquals(
                "MutableClock[2000-01-01T00:00:00Z,-18:00]",
                clockInMinOffset.toString(),
                "toString should show the shared instant and the new zone offset");
    }
}
