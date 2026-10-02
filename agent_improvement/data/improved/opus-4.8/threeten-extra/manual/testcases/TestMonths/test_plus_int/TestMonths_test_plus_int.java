package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#plus(int)}, which adds a number of months to an existing
 * {@code Months} amount and returns the resulting amount.
 */
public class TestMonths_test_plus_int {

    @Test
    public void test_plus_int() {
        Months fiveMonths = Months.of(5);

        // Adding zero leaves the amount unchanged.
        assertEquals(Months.of(5), fiveMonths.plus(0));

        // Adding a positive amount increases the total.
        assertEquals(Months.of(7), fiveMonths.plus(2));

        // Adding a negative amount decreases the total.
        assertEquals(Months.of(3), fiveMonths.plus(-2));

        // Adding is allowed right up to the int boundaries without overflowing.
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
