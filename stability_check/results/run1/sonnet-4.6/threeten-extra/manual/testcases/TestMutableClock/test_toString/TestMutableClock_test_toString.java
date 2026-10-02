package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_toString {

    // The toString format is: MutableClock[<instant>,<zone>]

    @Test
    public void toString_atEpochUTC_showsEpochInstantAndUTCZone() {
        MutableClock clock = MutableClock.epochUTC();
        assertEquals("MutableClock[1970-01-01T00:00:00Z,Z]", clock.toString());
    }

    @Test
    public void toString_afterAddingYears_reflectsUpdatedInstant() {
        MutableClock clock = MutableClock.epochUTC();
        clock.add(Period.ofYears(30));
        assertEquals("MutableClock[2000-01-01T00:00:00Z,Z]", clock.toString());
    }

    @Test
    public void toString_withNonUTCZone_showsOriginalInstantAndNewZone() {
        MutableClock clock = MutableClock.epochUTC();
        clock.add(Period.ofYears(30));
        MutableClock clockInMinZone = clock.withZone(ZoneOffset.MIN);
        // The instant is unchanged; only the zone offset differs in the string representation
        assertEquals("MutableClock[2000-01-01T00:00:00Z,-18:00]", clockInMinZone.toString());
    }
}
