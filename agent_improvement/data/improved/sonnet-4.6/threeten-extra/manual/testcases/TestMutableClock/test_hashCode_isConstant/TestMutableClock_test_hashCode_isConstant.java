package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_hashCode_isConstant {

    // MutableClock.hashCode() is derived from System.identityHashCode of its internal
    // InstantHolder (object identity) XOR-ed with the fixed zone's hash. Neither
    // component changes when the clock's instant is mutated, so the hash code must
    // remain constant for the lifetime of the clock instance regardless of time updates.
    @Test
    public void test_hashCode_isConstant() {
        MutableClock clock = MutableClock.epochUTC();
        int initialHashCode = clock.hashCode();

        // Mutate via add(TemporalAmount)
        clock.add(Period.ofMonths(1));
        assertEquals(initialHashCode, clock.hashCode(),
                "hashCode should not change after add(TemporalAmount)");

        // Mutate via add(long, TemporalUnit)
        clock.add(1, ChronoUnit.DAYS);
        assertEquals(initialHashCode, clock.hashCode(),
                "hashCode should not change after add(long, TemporalUnit)");

        // Mutate via set(TemporalAdjuster)
        clock.set(Year.of(2000));
        assertEquals(initialHashCode, clock.hashCode(),
                "hashCode should not change after set(TemporalAdjuster)");

        // Mutate via set(TemporalField, long)
        clock.set(ChronoField.INSTANT_SECONDS, -1);
        assertEquals(initialHashCode, clock.hashCode(),
                "hashCode should not change after set(TemporalField, long)");
    }
}
