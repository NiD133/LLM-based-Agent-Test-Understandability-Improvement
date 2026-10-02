package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_Period {

    @Test
    public void test_minus_TemporalAmount_Period() {
        Weeks fiveWeeks = Weeks.of(5);

        assertMinusPeriod(fiveWeeks, Period.ofWeeks(0), Weeks.of(5));
        assertMinusPeriod(fiveWeeks, Period.ofWeeks(2), Weeks.of(3));
        assertMinusPeriod(fiveWeeks, Period.ofWeeks(-2), Weeks.of(7));
        assertMinusPeriod(Weeks.of(Integer.MAX_VALUE - 1), Period.ofWeeks(-1), Weeks.of(Integer.MAX_VALUE));
        assertMinusPeriod(Weeks.of(Integer.MIN_VALUE + 1), Period.ofWeeks(1), Weeks.of(Integer.MIN_VALUE));
    }

    private static void assertMinusPeriod(Weeks baseWeeks, Period amountToSubtract, Weeks expectedWeeks) {
        assertEquals(expectedWeeks, baseWeeks.minus(amountToSubtract));
    }
}
