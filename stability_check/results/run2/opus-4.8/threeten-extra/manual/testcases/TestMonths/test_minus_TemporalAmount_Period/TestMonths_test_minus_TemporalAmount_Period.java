package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#minus(java.time.temporal.TemporalAmount)} when the amount
 * to subtract is supplied as a {@link Period} of months.
 */
public class TestMonths_test_minus_TemporalAmount_Period {

    @Test
    public void test_minus_TemporalAmount_Period() {
        Months fiveMonths = Months.of(5);

        // Subtracting zero months leaves the value unchanged.
        assertEquals(Months.of(5), fiveMonths.minus(Period.ofMonths(0)));
        // Subtracting a positive amount decreases the value: 5 - 2 = 3.
        assertEquals(Months.of(3), fiveMonths.minus(Period.ofMonths(2)));
        // Subtracting a negative amount increases the value: 5 - (-2) = 7.
        assertEquals(Months.of(7), fiveMonths.minus(Period.ofMonths(-2)));

        // Subtracting a negative amount can reach the maximum int value: (MAX_VALUE - 1) - (-1) = MAX_VALUE.
        assertEquals(
                Months.of(Integer.MAX_VALUE),
                Months.of(Integer.MAX_VALUE - 1).minus(Period.ofMonths(-1)));
        // Subtracting a positive amount can reach the minimum int value: (MIN_VALUE + 1) - 1 = MIN_VALUE.
        assertEquals(
                Months.of(Integer.MIN_VALUE),
                Months.of(Integer.MIN_VALUE + 1).minus(Period.ofMonths(1)));
    }
}
