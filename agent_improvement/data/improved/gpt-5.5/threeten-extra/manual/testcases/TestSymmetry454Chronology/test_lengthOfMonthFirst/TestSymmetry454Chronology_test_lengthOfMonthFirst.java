package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_lengthOfMonthFirst {

    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
                { 2000, 1, 28, 28 },
                { 2000, 2, 28, 35 },
                { 2000, 3, 28, 28 },
                { 2000, 4, 28, 28 },
                { 2000, 5, 28, 35 },
                { 2000, 6, 28, 28 },
                { 2000, 7, 28, 28 },
                { 2000, 8, 28, 35 },
                { 2000, 9, 28, 28 },
                { 2000, 10, 28, 28 },
                { 2000, 11, 28, 35 },
                { 2000, 12, 28, 28 },
                { 2004, 12, 20, 35 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonthFirst(int year, int month, int sampleDay, int expectedLength) {
        assertEquals(expectedLength, Symmetry454Date.of(year, month, 1).lengthOfMonth());
    }
}
