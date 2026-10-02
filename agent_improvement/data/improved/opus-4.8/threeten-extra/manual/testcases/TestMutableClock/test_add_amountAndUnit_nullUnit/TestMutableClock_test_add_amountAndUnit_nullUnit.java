package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#add(long, TemporalUnit)} rejects a null unit.
 */
public class TestMutableClock_test_add_amountAndUnit_nullUnit {

    @Test
    public void add_withNullUnit_throwsNullPointerException() {
        MutableClock clock = MutableClock.epochUTC();
        TemporalUnit nullUnit = null;

        assertThrows(
                NullPointerException.class,
                () -> clock.add(0, nullUnit));
    }
}
