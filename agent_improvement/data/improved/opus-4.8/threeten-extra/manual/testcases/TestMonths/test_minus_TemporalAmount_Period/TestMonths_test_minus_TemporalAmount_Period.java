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
        Months five = Months.of(5);

        // Subtracting zero leaves the amount unchanged.
        assertEquals(Months.of(5), five.minus(Period.ofMonths(0)));

        // Subtracting a positive period decreases the amount: 5 - 2 = 3.
        assertEquals(Months.of(3), five.minus(Period.ofMonths(2)));

        // Subtracting a negative period increases the amount: 5 - (-2) = 7.
        assertEquals(Months.of(7), five.minus(Period.ofMonths(-2)));

        // Subtraction is allowed up to the int boundaries: (MAX - 1) - (-1) = MAX.
        assertEquals(
                Months.of(Integer.MAX_VALUE),
                Months.of(Integer.MAX_VALUE - 1).minus(Period.ofMonths(-1)));

        // ... and down to the int minimum: (MIN + 1) - 1 = MIN.
        assertEquals(
                Months.of(Integer.MIN_VALUE),
                Months.of(Integer.MIN_VALUE + 1).minus(Period.ofMonths(1)));
    }
}
