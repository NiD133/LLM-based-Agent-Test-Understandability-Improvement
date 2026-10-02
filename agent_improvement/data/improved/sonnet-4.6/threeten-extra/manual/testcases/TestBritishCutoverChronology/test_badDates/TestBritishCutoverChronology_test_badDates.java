package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_badDates {

    public static Object[][] data_badDates() {
        return new Object[][] {
            // invalid month-of-year: zero and negative
            { 1900,  0,  0 },
            { 1900, -1,  1 },
            { 1900,  0,  1 },
            // invalid month-of-year: above 12
            { 1900, 13,  1 },
            { 1900, 14,  1 },
            // invalid day-of-month: zero and negative (January)
            { 1900,  1, -1 },
            { 1900,  1,  0 },
            // invalid day-of-month: exceeds January's 31 days
            { 1900,  1, 32 },
            // invalid day-of-month: zero and negative (February, Gregorian non-leap 1900)
            { 1900,  2, -1 },
            { 1900,  2,  0 },
            // invalid day-of-month: exceeds February's 28 days in non-leap year 1900
            { 1900,  2, 30 },
            { 1900,  2, 31 },
            { 1900,  2, 32 },
            // invalid day-of-month: zero and negative (February, Julian non-leap 1899)
            { 1899,  2, -1 },
            { 1899,  2,  0 },
            // invalid day-of-month: exceeds February's 28 days in non-leap year 1899
            { 1899,  2, 29 },
            { 1899,  2, 30 },
            { 1899,  2, 31 },
            { 1899,  2, 32 },
            // invalid day-of-month: zero, negative, and overflow for December
            { 1900, 12, -1 },
            { 1900, 12,  0 },
            { 1900, 12, 32 },
            // invalid day-of-month: overflow for remaining months
            { 1900,  3, 32 },  // March has 31 days
            { 1900,  4, 31 },  // April has 30 days
            { 1900,  5, 32 },  // May has 31 days
            { 1900,  6, 31 },  // June has 30 days
            { 1900,  7, 32 },  // July has 31 days
            { 1900,  8, 32 },  // August has 31 days
            { 1900,  9, 31 },  // September has 30 days
            { 1900, 10, 32 },  // October has 31 days
            { 1900, 11, 31 },  // November has 30 days
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> BritishCutoverDate.of(year, month, dom));
    }
}
