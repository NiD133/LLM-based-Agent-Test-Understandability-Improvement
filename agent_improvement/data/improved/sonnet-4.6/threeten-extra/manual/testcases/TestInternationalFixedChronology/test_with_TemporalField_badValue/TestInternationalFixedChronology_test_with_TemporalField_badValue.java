package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.TemporalField;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_with_TemporalField_badValue {

    /**
     * Test cases where calling {@code with(TemporalField, long)} should throw
     * {@link DateTimeException} because the supplied value is out of the valid range
     * for that field on the given date.
     *
     * Columns: year, month, dayOfMonth, field, invalidValue
     */
    public static Object[][] data_with_bad() {
        return new Object[][] {
            // ALIGNED_DAY_OF_WEEK_IN_MONTH: valid range for a normal month is [1, 7]
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 8 },
            { 2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0 },
            { 2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 8 },

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: valid range for a normal month is [1, 7]
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 8 },
            { 2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0 },
            { 2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 8 },

            // ALIGNED_WEEK_OF_MONTH: valid range for a normal month is [1, 4]
            { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH, 0 },
            { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH, 5 },
            { 2012, 1, 1, ALIGNED_WEEK_OF_MONTH, 0 },
            { 2012, 1, 1, ALIGNED_WEEK_OF_MONTH, 5 },

            // ALIGNED_WEEK_OF_YEAR: valid range is [1, 52]
            { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR, 0 },
            { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR, 53 },
            { 2012, 1, 1, ALIGNED_WEEK_OF_YEAR, 0 },
            { 2012, 1, 1, ALIGNED_WEEK_OF_YEAR, 53 },

            // DAY_OF_WEEK: valid range for a normal day is [1, 7]
            { 2013, 1, 1, DAY_OF_WEEK, 0 },
            { 2013, 1, 1, DAY_OF_WEEK, 8 },
            { 2012, 1, 1, DAY_OF_WEEK, 0 },
            { 2012, 1, 1, DAY_OF_WEEK, 8 },

            // DAY_OF_MONTH: out-of-range values for various month/year combinations
            { 2013, 1, 1, DAY_OF_MONTH, -1 },
            { 2013, 1, 1, DAY_OF_MONTH, 29 },   // non-leap month 1 has only 28 days
            { 2013, 6, 1, DAY_OF_MONTH, 29 },   // non-leap year: month 6 has only 28 days
            { 2012, 6, 1, DAY_OF_MONTH, 30 },   // leap year: month 6 has 29 days (leap day), not 30
            { 2012, 1, 1, DAY_OF_MONTH, -2 },
            { 2012, 1, 1, DAY_OF_MONTH, 29 },
            { 2013, 13, 1, DAY_OF_MONTH, 30 },  // month 13 (year day) has 29 days max
            { 2012, 13, 1, DAY_OF_MONTH, 30 },

            // DAY_OF_YEAR: valid range is [1, 365] or [1, 366] for leap years
            { 2013, 1, 1, DAY_OF_YEAR, 0 },
            { 2012, 1, 1, DAY_OF_YEAR, 0 },
            { 2013, 1, 1, DAY_OF_YEAR, 366 },   // non-leap year only has 365 days
            { 2012, 1, 1, DAY_OF_YEAR, 367 },   // leap year only has 366 days

            // EPOCH_DAY: values outside the supported calendar range
            { 2013, 1, 1, EPOCH_DAY, -719_529 },
            { 2013, 1, 1, EPOCH_DAY, 1_000_000 * 365L + 242_499 - 719_528 + 1 },

            // MONTH_OF_YEAR: valid range is [1, 13]
            { 2013, 1, 1, MONTH_OF_YEAR, -1 },
            { 2013, 1, 1, MONTH_OF_YEAR, 14 },
            { 2012, 1, 1, MONTH_OF_YEAR, -2 },
            { 2012, 1, 1, MONTH_OF_YEAR, 14 },

            // YEAR: year 0 is not valid (calendar starts at year 1)
            { 2013, 1, 1, YEAR, 0 },

            // Out-of-range values for week/day fields on a normal (non-special) day in a leap year
            // (day 21 of month 6 is an ordinary weekday — special values like 0 are invalid here)
            { 2012, 6, 21, DAY_OF_WEEK, 0 },
            { 2012, 6, 21, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0 },
            { 2012, 6, 21, ALIGNED_WEEK_OF_MONTH, 0 },
            { 2012, 6, 21, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0 },
            { 2012, 6, 21, ALIGNED_WEEK_OF_YEAR, 0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with_bad")
    public void test_with_TemporalField_badValue(int year, int month, int dom, TemporalField field, long value) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, month, dom).with(field, value));
    }
}
