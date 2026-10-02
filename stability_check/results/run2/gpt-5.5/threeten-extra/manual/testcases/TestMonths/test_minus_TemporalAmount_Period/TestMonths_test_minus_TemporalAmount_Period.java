package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_Period {

    @Test
    public void test_minus_TemporalAmount_Period() {
        Months fiveMonths = Months.of(5);

        assertMinusPeriod(Months.of(5), fiveMonths, Period.ofMonths(0));
        assertMinusPeriod(Months.of(3), fiveMonths, Period.ofMonths(2));
        assertMinusPeriod(Months.of(7), fiveMonths, Period.ofMonths(-2));

        assertMinusPeriod(
                Months.of(Integer.MAX_VALUE),
                Months.of(Integer.MAX_VALUE - 1),
                Period.ofMonths(-1));
        assertMinusPeriod(
                Months.of(Integer.MIN_VALUE),
                Months.of(Integer.MIN_VALUE + 1),
                Period.ofMonths(1));
    }

    private static void assertMinusPeriod(Months expected, Months base, Period periodToSubtract) {
        assertEquals(expected, base.minus(periodToSubtract));
    }
}
