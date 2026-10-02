package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_TemporalAmount_Period {

    @Test
    public void test_minus_TemporalAmount_Period() {
        Days fiveDays = Days.of(5);

        assertEquals(Days.of(5), fiveDays.minus(Period.ofDays(0)));
        assertEquals(Days.of(3), fiveDays.minus(Period.ofDays(2)));
        assertEquals(Days.of(7), fiveDays.minus(Period.ofDays(-2)));

        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).minus(Period.ofDays(-1)));
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).minus(Period.ofDays(1)));
    }
}
