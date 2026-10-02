package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_badDates {

    public static Object[][] data_badDates() {
        return new Object[][] {
                { -1, 13, 28 },
                { -1, 13, 29 },
                { 0, 1, 1 },
                { 1900, -2, 1 },
                { 1900, 14, 1 },
                { 1900, 15, 1 },
                { 1900, 1, -1 },
                { 1900, 1, 0 },
                { 1900, 1, 29 },
                { 1904, -1, -2 },
                { 1904, -1, 0 },
                { 1904, -1, 1 },
                { 1900, -1, 0 },
                { 1900, -1, -2 },
                { 1900, 0, -1 },
                { 1900, 0, 1 },
                { 1900, 0, 2 },
                { 1900, 2, 29 },
                { 1900, 3, 29 },
                { 1900, 4, 29 },
                { 1900, 5, 29 },
                { 1900, 6, 29 },
                { 1900, 7, 29 },
                { 1900, 8, 29 },
                { 1900, 9, 29 },
                { 1900, 10, 29 },
                { 1900, 11, 29 },
                { 1900, 12, 29 },
                { 1900, 13, 30 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, month, dom));
    }
}
