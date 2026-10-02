package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Quarter#plus(long)}, which adds a number of quarters, rolling
 * around the end of the year (Q4 + 1 quarter wraps back to Q1).
 */
public class TestQuarter_test_plus_long {

    /**
     * Cases for {@link #test_plus_long}.
     * <p>
     * Each row is {@code { startQuarter, quartersToAdd, expectedQuarter }}.
     * All cases start from Q1 and sweep the offset from -5 to +5 to confirm
     * the result wraps correctly in both directions.
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            { 1, -5, 4 },
            { 1, -4, 1 },
            { 1, -3, 2 },
            { 1, -2, 3 },
            { 1, -1, 4 },
            { 1,  0, 1 },
            { 1,  1, 2 },
            { 1,  2, 3 },
            { 1,  3, 4 },
            { 1,  4, 1 },
            { 1,  5, 2 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_long(int startQuarter, long quartersToAdd, int expectedQuarter) {
        Quarter result = Quarter.of(startQuarter).plus(quartersToAdd);

        assertEquals(Quarter.of(expectedQuarter), result);
    }
}
