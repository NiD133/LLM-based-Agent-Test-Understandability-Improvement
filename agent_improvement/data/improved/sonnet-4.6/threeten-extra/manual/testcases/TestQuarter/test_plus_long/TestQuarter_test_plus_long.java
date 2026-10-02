package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestQuarter_test_plus_long {

    /**
     * Test data for {@link Quarter#plus(long)}: {baseQuarter, amountToAdd, expectedQuarter}.
     * Quarters cycle through 1–4 and wrap around at both ends.
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // Negative amounts: subtracting quarters wraps backward
            { 1, -5, 4 },   // Q1 - 5 quarters = Q4 (wraps past start)
            { 1, -4, 1 },   // Q1 - 4 quarters = Q1 (full cycle, back to start)
            { 1, -3, 2 },   // Q1 - 3 quarters = Q2
            { 1, -2, 3 },   // Q1 - 2 quarters = Q3
            { 1, -1, 4 },   // Q1 - 1 quarter  = Q4

            // Zero: no change
            { 1,  0, 1 },   // Q1 + 0 quarters = Q1

            // Positive amounts: adding quarters wraps forward
            { 1,  1, 2 },   // Q1 + 1 quarter  = Q2
            { 1,  2, 3 },   // Q1 + 2 quarters = Q3
            { 1,  3, 4 },   // Q1 + 3 quarters = Q4
            { 1,  4, 1 },   // Q1 + 4 quarters = Q1 (full cycle, back to start)
            { 1,  5, 2 },   // Q1 + 5 quarters = Q2 (wraps past end)
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_long(int base, long amount, int expected) {
        assertEquals(Quarter.of(expected), Quarter.of(base).plus(amount));
    }
}
