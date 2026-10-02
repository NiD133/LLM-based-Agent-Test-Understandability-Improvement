package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_badDates {

    /**
     * Invalid (year, month, day-of-month) triples that must each throw DateTimeException.
     *
     * Symmetry010 month lengths:
     *   Jan=30, Feb=31, Mar=30, Apr=30, May=31, Jun=30,
     *   Jul=30, Aug=31, Sep=30, Oct=30, Nov=31, Dec=30 (37 in a leap year)
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // month out of range
            { -1, 13, 28 },
            { -1, 13, 29 },
            { 2000, -2,  1 },
            { 2000, 13,  1 },
            { 2000, 15,  1 },
            { 2000,  0,  1 },
            { 2000, -1,  1 },

            // day-of-month out of range
            { 2000,  1, -1 },
            { 2000,  1,  0 },
            { 2000, -1,  0 },

            // day-of-month exceeds each month's length
            { 2000,  1, 31 },   // Jan has 30 days
            { 2000,  2, 32 },   // Feb has 31 days
            { 2000,  3, 31 },   // Mar has 30 days
            { 2000,  4, 31 },   // Apr has 30 days
            { 2000,  5, 32 },   // May has 31 days
            { 2000,  6, 31 },   // Jun has 30 days
            { 2000,  7, 31 },   // Jul has 30 days
            { 2000,  8, 32 },   // Aug has 31 days
            { 2000,  9, 31 },   // Sep has 30 days
            { 2000, 10, 31 },   // Oct has 30 days
            { 2000, 11, 32 },   // Nov has 31 days
            { 2000, 12, 31 },   // Dec has 30 days in a non-leap year

            // day-of-month exceeds December's extended length in a leap year (37 days)
            { 2004, 12, 38 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> Symmetry010Date.of(year, month, dom));
    }
}
