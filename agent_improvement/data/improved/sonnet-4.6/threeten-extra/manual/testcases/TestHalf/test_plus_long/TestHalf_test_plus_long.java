package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestHalf_test_plus_long {

    // Each row: { baseHalf, halvesToAdd, expectedHalf }
    // The result wraps cyclically between H1 (1) and H2 (2).
    public static Object[][] data_plus() {
        return new Object[][] {
            { 1, -4, 1 },
            { 1, -3, 2 },
            { 1, -2, 1 },
            { 1, -1, 2 },
            { 1,  0, 1 },
            { 1,  1, 2 },
            { 1,  2, 1 },
            { 1,  3, 2 },
            { 1,  4, 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_long(int base, long amount, int expected) {
        assertEquals(Half.of(expected), Half.of(base).plus(amount));
    }
}
