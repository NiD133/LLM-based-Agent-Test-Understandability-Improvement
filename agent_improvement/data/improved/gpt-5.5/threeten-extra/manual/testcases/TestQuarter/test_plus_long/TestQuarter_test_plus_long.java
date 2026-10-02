package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestQuarter_test_plus_long {

    public static Object[][] data_plus() {
        return new Object[][] {
                {1, -5, 4},
                {1, -4, 1},
                {1, -3, 2},
                {1, -2, 3},
                {1, -1, 4},
                {1, 0, 1},
                {1, 1, 2},
                {1, 2, 3},
                {1, 3, 4},
                {1, 4, 1},
                {1, 5, 2},
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_long(int baseQuarter, long quartersToAdd, int expectedQuarter) {
        Quarter actualQuarter = Quarter.of(baseQuarter).plus(quartersToAdd);

        assertEquals(Quarter.of(expectedQuarter), actualQuarter);
    }
}
