package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Half#plus(long)}.
 * <p>
 * Adding a number of halves rolls around the end of the year, so the result
 * cycles with a period of two: an even offset returns the same half, an odd
 * offset returns the other half.
 */
public class TestHalf_test_plus_long {

    /**
     * Cases for {@link #test_plus_long}, each as {@code {startHalf, halvesToAdd, expectedHalf}}.
     * <p>
     * Every case starts from H1 and adds an offset from -4 to +4; the expected
     * half alternates between H1 (even offset) and H2 (odd offset).
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            {1, -4, 1},
            {1, -3, 2},
            {1, -2, 1},
            {1, -1, 2},
            {1, 0, 1},
            {1, 1, 2},
            {1, 2, 1},
            {1, 3, 2},
            {1, 4, 1},
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_long(int startHalf, long halvesToAdd, int expectedHalf) {
        Half result = Half.of(startHalf).plus(halvesToAdd);
        assertEquals(Half.of(expectedHalf), result);
    }
}
