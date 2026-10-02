package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Months#of(int)}.
 * <p>
 * Verifies that the factory stores the supplied month count unchanged,
 * across zero, small positive/negative values and the integer extremes.
 */
public class TestMonths_test_of {

    @Test
    public void of_storesTheGivenNumberOfMonths() {
        assertEquals(0, Months.of(0).getAmount());
        assertEquals(1, Months.of(1).getAmount());
        assertEquals(2, Months.of(2).getAmount());
        assertEquals(-1, Months.of(-1).getAmount());
        assertEquals(-2, Months.of(-2).getAmount());

        // Boundary values: the largest and smallest possible month counts.
        assertEquals(Integer.MAX_VALUE, Months.of(Integer.MAX_VALUE).getAmount());
        assertEquals(Integer.MIN_VALUE, Months.of(Integer.MIN_VALUE).getAmount());
    }
}
