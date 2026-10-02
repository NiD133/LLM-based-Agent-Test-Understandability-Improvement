package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestMonths_test_abs {

    public static Object[][] absoluteValueCases() {
        return new Object[][] {
            {0, 0},
            {12, 12},
            {-12, 12},
            {Integer.MAX_VALUE, Integer.MAX_VALUE},
            {-Integer.MAX_VALUE, Integer.MAX_VALUE}
        };
    }

    @ParameterizedTest
    @MethodSource("absoluteValueCases")
    public void test_abs(int inputMonths, int expectedAbsoluteMonths) {
        assertEquals(Months.of(expectedAbsoluteMonths), Months.of(inputMonths).abs());
    }
}
