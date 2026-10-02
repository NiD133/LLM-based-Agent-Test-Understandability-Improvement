package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_lengthOfMonth {

    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
                {1900, 1, 31},
                {1900, 2, 29},
                {1900, 3, 31},
                {1900, 4, 30},
                {1900, 5, 31},
                {1900, 6, 30},
                {1900, 7, 31},
                {1900, 8, 31},
                {1900, 9, 30},
                {1900, 10, 31},
                {1900, 11, 30},
                {1900, 12, 31},
                {1901, 2, 28},
                {1902, 2, 28},
                {1903, 2, 28},
                {1904, 2, 29},
                {2000, 2, 29},
                {2100, 2, 29},
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, JulianDate.of(year, month, 1).lengthOfMonth());
    }
}
