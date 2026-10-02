package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_add_amountAndUnit_nullUnit {

    @Test
    public void test_add_amountAndUnit_nullUnit() {
        MutableClock clock = MutableClock.epochUTC();
        TemporalUnit nullUnit = null;
        assertThrows(NullPointerException.class, () -> clock.add(0, nullUnit));
    }
}
