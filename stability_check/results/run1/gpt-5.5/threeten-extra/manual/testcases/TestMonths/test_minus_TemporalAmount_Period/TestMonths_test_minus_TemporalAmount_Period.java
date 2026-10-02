package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_Period {

    @Test
    public void test_minus_TemporalAmount_Period() {
        Months test5 = Months.of(5);

        assertEquals(Months.of(5), test5.minus(Period.ofMonths(0)));
        assertEquals(Months.of(3), test5.minus(Period.ofMonths(2)));
        assertEquals(Months.of(7), test5.minus(Period.ofMonths(-2)));

        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).minus(Period.ofMonths(-1)));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).minus(Period.ofMonths(1)));
    }
}
