package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_badDates {

    public static Object[][] data_badDates() {
        return new Object[][] {
                { -1, 13, 28 },
                { -1, 13, 29 },
                { 2000, -2, 1 },
                { 2000, 13, 1 },
                { 2000, 15, 1 },
                { 2000, 1, -1 },
                { 2000, 1, 0 },
                { 2000, 0, 1 },
                { 2000, -1, 0 },
                { 2000, -1, 1 },
                { 2000, 1, 29 },
                { 2000, 2, 36 },
                { 2000, 3, 29 },
                { 2000, 4, 29 },
                { 2000, 5, 36 },
                { 2000, 6, 29 },
                { 2000, 7, 29 },
                { 2000, 8, 36 },
                { 2000, 9, 29 },
                { 2000, 10, 29 },
                { 2000, 11, 36 },
                { 2000, 12, 29 },
                { 2004, 12, 36 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dayOfMonth) {
        assertThrows(
                DateTimeException.class,
                () -> Symmetry454Date.of(year, month, dayOfMonth));
    }
}
