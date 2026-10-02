package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestHalf_test_minus_long {

    public static Object[][] data_minus() {
        return new Object[][] {
                {1, -4, 1},
                {1, -3, 2},
                {1, -2, 1},
                {1, -1, 2},
                {1, 0, 1},
                {1, 1, 2},
                {1, 2, 1},
                {1, 3, 2},
                {1, 4, 1}
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_long(int base, long amount, int expected) {
        Half actualHalf = Half.of(base).minus(amount);

        assertEquals(Half.of(expected), actualHalf);
    }
}
