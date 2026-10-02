package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_toString {

    // MutableClock.toString() format: "MutableClock[<instant>,<zone>]"

    @Test
    public void test_toString() {
        // Initial state: epoch in UTC
        MutableClock clock = MutableClock.epochUTC();
        assertEquals("MutableClock[1970-01-01T00:00:00Z,Z]", clock.toString());

        // After advancing 30 years the instant portion updates, zone stays UTC
        clock.add(Period.ofYears(30));
        assertEquals("MutableClock[2000-01-01T00:00:00Z,Z]", clock.toString());

        // withZone shares the same instant but displays a different zone offset
        MutableClock clockAtMinZone = clock.withZone(ZoneOffset.MIN);
        assertEquals("MutableClock[2000-01-01T00:00:00Z,-18:00]", clockAtMinZone.toString());
    }
}
