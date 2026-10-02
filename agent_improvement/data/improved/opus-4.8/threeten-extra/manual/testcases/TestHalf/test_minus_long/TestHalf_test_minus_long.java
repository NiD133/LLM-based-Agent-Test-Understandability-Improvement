package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Half#minus(long)}.
 * <p>
 * Subtracting halves rolls around the start of the year, so the result cycles
 * with a period of two (H1, H2, H1, H2, ...). Every case below starts from H1.
 */
public class TestHalf_test_minus_long {

    /**
     * Cases of {@code [startingHalf, halvesToSubtract, expectedHalf]}.
     * <p>
     * Starting from H1, subtracting any number of halves alternates between
     * H1 (even offsets) and H2 (odd offsets), for both positive and negative amounts.
     */
    public static Object[][] data_minus() {
        return new Object[][] {
            { 1, -4, 1 },
            { 1, -3, 2 },
            { 1, -2, 1 },
            { 1, -1, 2 },
            { 1, 0, 1 },
            { 1, 1, 2 },
            { 1, 2, 1 },
            { 1, 3, 2 },
            { 1, 4, 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_long(int startingHalf, long halvesToSubtract, int expectedHalf) {
        Half result = Half.of(startingHalf).minus(halvesToSubtract);

        assertEquals(Half.of(expectedHalf), result);
    }
}
