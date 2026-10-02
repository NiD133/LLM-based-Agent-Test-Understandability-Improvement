package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_badDates {

    public static Object[][] data_badDates() {
        return new Object[][] {
            // Month must be in the supported Pax month range.
            {1900, 0, 0},
            {1900, -1, 1},
            {1900, 0, 1},
            {1900, 15, 1},
            {1900, 16, 1},

            // Normal months contain days 1 through 28.
            {1900, 1, -1},
            {1900, 1, 0},
            {1900, 1, 29},

            // Month 13 is short in 1900.
            {1900, 13, -1},
            {1900, 13, 0},
            {1900, 13, 8},

            // Month 14 contains days 1 through 28 in 1900.
            {1900, 14, -1},
            {1900, 14, 0},
            {1900, 14, 29},
            {1900, 14, 30},

            // Month 13 and 14 boundaries differ in 1898.
            {1898, 13, -1},
            {1898, 13, 0},
            {1898, 14, 29},
            {1898, 14, 30},
            {1898, 14, 1},
            {1898, 14, 2},

            // Duplicate cases retained from the original test data.
            {1900, 14, -1},
            {1900, 14, 0},
            {1900, 14, 29},

            // Every regular month rejects day 29.
            {1900, 2, 29},
            {1900, 3, 29},
            {1900, 4, 29},
            {1900, 5, 29},
            {1900, 6, 29},
            {1900, 7, 29},
            {1900, 8, 29},
            {1900, 9, 29},
            {1900, 10, 29},
            {1900, 11, 29},
            {1900, 12, 29}
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> PaxDate.of(year, month, dom));
    }
}
