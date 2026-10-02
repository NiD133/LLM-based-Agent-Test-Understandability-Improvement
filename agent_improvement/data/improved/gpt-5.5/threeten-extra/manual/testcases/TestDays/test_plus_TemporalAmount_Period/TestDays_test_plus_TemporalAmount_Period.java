package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_TemporalAmount_Period() {
        Days fiveDays = Days.of(5);

        assertPeriodAddition(Days.of(5), fiveDays, Period.ofDays(0));
        assertPeriodAddition(Days.of(7), fiveDays, Period.ofDays(2));
        assertPeriodAddition(Days.of(3), fiveDays, Period.ofDays(-2));
        assertPeriodAddition(
                Days.of(Integer.MAX_VALUE),
                Days.of(Integer.MAX_VALUE - 1),
                Period.ofDays(1));
        assertPeriodAddition(
                Days.of(Integer.MIN_VALUE),
                Days.of(Integer.MIN_VALUE + 1),
                Period.ofDays(-1));
    }

    private static void assertPeriodAddition(Days expected, Days baseDays, Period periodToAdd) {
        assertEquals(expected, baseDays.plus(periodToAdd));
    }
}
