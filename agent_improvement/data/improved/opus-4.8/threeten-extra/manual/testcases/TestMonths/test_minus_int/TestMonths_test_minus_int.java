package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#minus(int)}, which returns a new {@code Months}
 * holding this amount reduced by the given number of months.
 */
public class TestMonths_test_minus_int {

    @Test
    public void minus_int_subtractsTheGivenNumberOfMonths() {
        Months fiveMonths = Months.of(5);

        // Subtracting zero leaves the amount unchanged.
        assertEquals(Months.of(5), fiveMonths.minus(0));
        // Subtracting a positive amount decreases the total.
        assertEquals(Months.of(3), fiveMonths.minus(2));
        // Subtracting a negative amount increases the total.
        assertEquals(Months.of(7), fiveMonths.minus(-2));

        // Subtracting at the int boundaries stays within range (no overflow).
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
