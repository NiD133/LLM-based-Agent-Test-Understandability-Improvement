package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_toString {

    @Test
    public void test_toString() {
        MutableClock clock = MutableClock.epochUTC();
        assertEquals("MutableClock[1970-01-01T00:00:00Z,Z]", clock.toString());

        clock.add(Period.ofYears(30));
        assertEquals("MutableClock[2000-01-01T00:00:00Z,Z]", clock.toString());

        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);
        assertEquals("MutableClock[2000-01-01T00:00:00Z,-18:00]", withOtherZone.toString());
    }
}
