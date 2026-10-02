package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#hashCode()} stays constant across mutations.
 *
 * <p>A {@code MutableClock}'s hash code is derived from the identity of its
 * internal instant holder and its time-zone, neither of which changes when the
 * clock's instant is updated. Therefore mutating the instant (via any of the
 * update methods) must never change the hash code.
 */
public class TestMutableClock_test_hashCode_isConstant {

    @Test
    public void test_hashCode_isConstant() {
        MutableClock clock = MutableClock.epochUTC();

        // Capture the hash code before any mutation, then confirm it is
        // unaffected by each kind of clock update.
        int expectedHash = clock.hashCode();

        // add(TemporalAmount)
        clock.add(Period.ofMonths(1));
        assertEquals(expectedHash, clock.hashCode());

        // add(long, TemporalUnit)
        clock.add(1, ChronoUnit.DAYS);
        assertEquals(expectedHash, clock.hashCode());

        // set(TemporalAdjuster)
        clock.set(Year.of(2000));
        assertEquals(expectedHash, clock.hashCode());

        // set(TemporalField, long)
        clock.set(ChronoField.INSTANT_SECONDS, -1);
        assertEquals(expectedHash, clock.hashCode());
    }
}
