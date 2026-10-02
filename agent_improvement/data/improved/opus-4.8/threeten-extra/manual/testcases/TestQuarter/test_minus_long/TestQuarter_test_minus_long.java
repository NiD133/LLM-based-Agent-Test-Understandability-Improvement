package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Quarter#minus(long)}.
 * <p>
 * Subtracting quarters rolls around the start of the year (Q1 wraps back to Q4),
 * and the amount may be negative. Every case below starts from Q1.
 */
public class TestQuarter_test_minus_long {

    /**
     * Cases of {@code {startQuarter, quartersToSubtract, expectedQuarter}}, all starting from Q1.
     */
    public static Object[][] data_minus() {
        return new Object[][] {
            {1, -5, 2},
            {1, -4, 1},
            {1, -3, 4},
            {1, -2, 3},
            {1, -1, 2},
            {1, 0, 1},
            {1, 1, 4},
            {1, 2, 3},
            {1, 3, 2},
            {1, 4, 1},
            {1, 5, 4},
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_long(int startQuarter, long quartersToSubtract, int expectedQuarter) {
        Quarter result = Quarter.of(startQuarter).minus(quartersToSubtract);
        assertEquals(Quarter.of(expectedQuarter), result);
    }
}
