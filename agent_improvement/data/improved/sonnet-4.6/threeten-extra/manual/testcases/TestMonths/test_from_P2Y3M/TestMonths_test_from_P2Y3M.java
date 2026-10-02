package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_from_P2Y3M {

    /**
     * Verifies that {@code Months.from} correctly converts a mixed years-and-months
     * temporal amount into a total month count.
     * P2Y3M = 2 years × 12 months/year + 3 months = 27 months.
     */
    @Test
    public void test_from_P2Y3M() {
        int expectedMonths = 2 * 12 + 3; // 27
        assertEquals(Months.of(expectedMonths), Months.from(new MockYearsMonths(2, 3)));
    }
}
