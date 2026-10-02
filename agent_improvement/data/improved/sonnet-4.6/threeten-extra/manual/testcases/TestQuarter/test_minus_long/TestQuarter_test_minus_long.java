package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestQuarter_test_minus_long {

    /**
     * Test data for {@link #test_minus_long}: each row is
     * {baseQuarter, amountToSubtract, expectedQuarter}.
     *
     * All cases start from Q1 (value 1) and subtract various amounts,
     * including negative amounts (which effectively advance the quarter forward).
     * Quarters wrap cyclically: subtracting past Q1 wraps back to Q4.
     */
    public static Object[][] data_minus() {
        return new Object[][] {
            // { base, amountToSubtract, expected }
            { 1, -5, 2 },  // Q1 - (-5) = Q1 + 5 quarters = Q2
            { 1, -4, 1 },  // Q1 - (-4) = Q1 + 4 quarters = Q1 (full cycle)
            { 1, -3, 4 },  // Q1 - (-3) = Q1 + 3 quarters = Q4
            { 1, -2, 3 },  // Q1 - (-2) = Q1 + 2 quarters = Q3
            { 1, -1, 2 },  // Q1 - (-1) = Q1 + 1 quarter  = Q2
            { 1,  0, 1 },  // Q1 -   0  = Q1              (no change)
            { 1,  1, 4 },  // Q1 -   1  = Q4              (wrap)
            { 1,  2, 3 },  // Q1 -   2  = Q3
            { 1,  3, 2 },  // Q1 -   3  = Q2
            { 1,  4, 1 },  // Q1 -   4  = Q1              (full cycle)
            { 1,  5, 4 },  // Q1 -   5  = Q4
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_long(int base, long amount, int expected) {
        assertEquals(Quarter.of(expected), Quarter.of(base).minus(amount));
    }
}
