package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#plus(java.time.temporal.TemporalAmount)} when the added
 * amount is a {@link Period} expressed purely in months.
 */
public class TestMonths_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_TemporalAmount_Period() {
        Months fiveMonths = Months.of(5);

        // Adding zero months leaves the value unchanged.
        assertEquals(Months.of(5), fiveMonths.plus(Period.ofMonths(0)));

        // Adding a positive amount increases the value: 5 + 2 = 7.
        assertEquals(Months.of(7), fiveMonths.plus(Period.ofMonths(2)));

        // Adding a negative amount decreases the value: 5 + (-2) = 3.
        assertEquals(Months.of(3), fiveMonths.plus(Period.ofMonths(-2)));

        // Adding exactly reaches Integer.MAX_VALUE without overflowing.
        assertEquals(
                Months.of(Integer.MAX_VALUE),
                Months.of(Integer.MAX_VALUE - 1).plus(Period.ofMonths(1)));

        // Adding a negative amount exactly reaches Integer.MIN_VALUE without overflowing.
        assertEquals(
                Months.of(Integer.MIN_VALUE),
                Months.of(Integer.MIN_VALUE + 1).plus(Period.ofMonths(-1)));
    }
}
