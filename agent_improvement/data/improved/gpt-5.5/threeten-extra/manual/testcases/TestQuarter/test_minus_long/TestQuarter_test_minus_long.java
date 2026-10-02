package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestQuarter_test_minus_long {

    private static final int BASE_QUARTER = 1;

    public static Object[][] data_minus() {
        return new Object[][] {
                { BASE_QUARTER, -5, 2 },
                { BASE_QUARTER, -4, 1 },
                { BASE_QUARTER, -3, 4 },
                { BASE_QUARTER, -2, 3 },
                { BASE_QUARTER, -1, 2 },
                { BASE_QUARTER, 0, 1 },
                { BASE_QUARTER, 1, 4 },
                { BASE_QUARTER, 2, 3 },
                { BASE_QUARTER, 3, 2 },
                { BASE_QUARTER, 4, 1 },
                { BASE_QUARTER, 5, 4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_long(int base, long amount, int expected) {
        assertEquals(Quarter.of(expected), Quarter.of(base).minus(amount));
    }
}
