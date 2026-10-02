package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_hashCode_isConstant {

    @Test
    public void test_hashCode_isConstant() {
        MutableClock clock = MutableClock.epochUTC();
        int originalHashCode = clock.hashCode();

        clock.add(Period.ofMonths(1));
        assertEquals(originalHashCode, clock.hashCode());

        clock.add(1, ChronoUnit.DAYS);
        assertEquals(originalHashCode, clock.hashCode());

        clock.set(Year.of(2000));
        assertEquals(originalHashCode, clock.hashCode());

        clock.set(ChronoField.INSTANT_SECONDS, -1);
        assertEquals(originalHashCode, clock.hashCode());
    }
}
