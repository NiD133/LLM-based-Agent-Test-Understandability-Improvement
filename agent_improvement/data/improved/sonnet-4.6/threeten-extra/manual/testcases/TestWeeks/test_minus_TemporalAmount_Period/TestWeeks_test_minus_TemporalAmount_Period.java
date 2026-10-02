package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_Period {

    @Test
    public void test_minus_TemporalAmount_Period_subtractZero() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals(Weeks.of(5), fiveWeeks.minus(Period.ofWeeks(0)));
    }

    @Test
    public void test_minus_TemporalAmount_Period_subtractPositive() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals(Weeks.of(3), fiveWeeks.minus(Period.ofWeeks(2)));
    }

    @Test
    public void test_minus_TemporalAmount_Period_subtractNegative() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals(Weeks.of(7), fiveWeeks.minus(Period.ofWeeks(-2)));
    }

    @Test
    public void test_minus_TemporalAmount_Period_upperBoundary() {
        Weeks nearMax = Weeks.of(Integer.MAX_VALUE - 1);
        assertEquals(Weeks.of(Integer.MAX_VALUE), nearMax.minus(Period.ofWeeks(-1)));
    }

    @Test
    public void test_minus_TemporalAmount_Period_lowerBoundary() {
        Weeks nearMin = Weeks.of(Integer.MIN_VALUE + 1);
        assertEquals(Weeks.of(Integer.MIN_VALUE), nearMin.minus(Period.ofWeeks(1)));
    }
}
