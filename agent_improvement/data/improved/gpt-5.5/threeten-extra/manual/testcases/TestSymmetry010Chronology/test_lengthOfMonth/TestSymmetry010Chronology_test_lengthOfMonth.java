package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_lengthOfMonth {

    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            { 2000, 1, 28, 30 },
            { 2000, 2, 28, 31 },
            { 2000, 3, 28, 30 },
            { 2000, 4, 28, 30 },
            { 2000, 5, 28, 31 },
            { 2000, 6, 28, 30 },
            { 2000, 7, 28, 30 },
            { 2000, 8, 28, 31 },
            { 2000, 9, 28, 30 },
            { 2000, 10, 28, 30 },
            { 2000, 11, 28, 31 },
            { 2000, 12, 28, 30 },
            { 2004, 12, 20, 37 }
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int day, int length) {
        assertEquals(length, Symmetry010Date.of(year, month, day).lengthOfMonth());
    }
}
