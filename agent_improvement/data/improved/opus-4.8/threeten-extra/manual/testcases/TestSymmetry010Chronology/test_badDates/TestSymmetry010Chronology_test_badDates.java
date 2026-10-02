package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Symmetry010Date#of(int, int, int)} rejects invalid
 * year/month/day combinations by throwing a {@link DateTimeException}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_badDates {

    /**
     * Supplies {year, month, dayOfMonth} triples that do not form a valid
     * Symmetry010 date. The cases cover out-of-range months, out-of-range
     * days, and days that exceed the actual length of an otherwise valid month.
     *
     * @return the invalid date components, one triple per test invocation
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // Month 13 does not exist (only months 1-12 are valid).
            { -1, 13, 28 },
            { -1, 13, 29 },

            // Months outside the 1-12 range.
            { 2000, -2, 1 },
            { 2000, 13, 1 },
            { 2000, 15, 1 },
            { 2000, 0, 1 },
            { 2000, -1, 0 },
            { 2000, -1, 1 },

            // Days outside the valid lower bound (must be >= 1).
            { 2000, 1, -1 },
            { 2000, 1, 0 },

            // Days that exceed the length of the given month.
            // 30-day months: 1, 3, 4, 6, 7, 9, 10, 12.
            { 2000, 1, 31 },
            { 2000, 3, 31 },
            { 2000, 4, 31 },
            { 2000, 6, 31 },
            { 2000, 7, 31 },
            { 2000, 9, 31 },
            { 2000, 10, 31 },
            { 2000, 12, 31 },
            // 31-day months: 2, 5, 8, 11.
            { 2000, 2, 32 },
            { 2000, 5, 32 },
            { 2000, 8, 32 },
            { 2000, 11, 32 },

            // 2004 is a leap year, so December has 37 days; day 38 is invalid.
            { 2004, 12, 38 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> Symmetry010Date.of(year, month, dom));
    }
}
