package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_toString {

    private static final String EPOCH_UTC_DESCRIPTION = "MutableClock[1970-01-01T00:00:00Z,Z]";
    private static final String YEAR_2000_UTC_DESCRIPTION = "MutableClock[2000-01-01T00:00:00Z,Z]";
    private static final String YEAR_2000_MIN_OFFSET_DESCRIPTION = "MutableClock[2000-01-01T00:00:00Z,-18:00]";

    @Test
    public void test_toString() {
        MutableClock clock = MutableClock.epochUTC();
        assertEquals(EPOCH_UTC_DESCRIPTION, clock.toString());

        clock.add(Period.ofYears(30));
        assertEquals(YEAR_2000_UTC_DESCRIPTION, clock.toString());

        MutableClock sameInstantAtMinimumOffset = clock.withZone(ZoneOffset.MIN);
        assertEquals(YEAR_2000_MIN_OFFSET_DESCRIPTION, sameInstantAtMinimumOffset.toString());
    }
}
