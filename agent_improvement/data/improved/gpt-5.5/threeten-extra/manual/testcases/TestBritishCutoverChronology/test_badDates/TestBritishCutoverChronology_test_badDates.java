package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_badDates {

    public static Object[][] data_badDates() {
        return new Object[][] {
                {1900, 0, 0},
                {1900, -1, 1},
                {1900, 0, 1},
                {1900, 13, 1},
                {1900, 14, 1},
                {1900, 1, -1},
                {1900, 1, 0},
                {1900, 1, 32},
                {1900, 2, -1},
                {1900, 2, 0},
                {1900, 2, 30},
                {1900, 2, 31},
                {1900, 2, 32},
                {1899, 2, -1},
                {1899, 2, 0},
                {1899, 2, 29},
                {1899, 2, 30},
                {1899, 2, 31},
                {1899, 2, 32},
                {1900, 12, -1},
                {1900, 12, 0},
                {1900, 12, 32},
                {1900, 3, 32},
                {1900, 4, 31},
                {1900, 5, 32},
                {1900, 6, 31},
                {1900, 7, 32},
                {1900, 8, 32},
                {1900, 9, 31},
                {1900, 10, 32},
                {1900, 11, 31},
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dayOfMonth) {
        assertThrows(DateTimeException.class, () -> BritishCutoverDate.of(year, month, dayOfMonth));
    }
}
