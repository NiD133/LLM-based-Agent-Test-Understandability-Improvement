package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#plus(java.time.temporal.TemporalAmount)} when the amount
 * added is itself a {@code Months} instance.
 */
public class TestMonths_test_plus_TemporalAmount_Months {

    @Test
    public void test_plus_TemporalAmount_Months() {
        Months five = Months.of(5);

        // Adding zero leaves the amount unchanged.
        assertEquals(Months.of(5), five.plus(Months.of(0)));

        // Adding a positive amount increases the total: 5 + 2 = 7.
        assertEquals(Months.of(7), five.plus(Months.of(2)));

        // Adding a negative amount decreases the total: 5 + (-2) = 3.
        assertEquals(Months.of(3), five.plus(Months.of(-2)));

        // Adding right up to the int boundaries must not overflow.
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).plus(Months.of(1)));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).plus(Months.of(-1)));
    }
}
