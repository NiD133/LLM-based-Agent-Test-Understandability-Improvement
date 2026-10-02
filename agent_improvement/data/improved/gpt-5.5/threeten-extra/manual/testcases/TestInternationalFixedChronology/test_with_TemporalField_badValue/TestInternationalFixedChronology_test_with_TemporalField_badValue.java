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

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_with_TemporalField_badValue {

    public static Object[][] data_with_bad() {
        return new Object[][] {
                // Aligned day values must be either the special day value 0 or a normal week day.
                {2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0},
                {2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 8},
                {2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0},
                {2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 8},
                {2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0},
                {2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 8},
                {2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0},
                {2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 8},

                // Aligned week values must stay inside the month or year range.
                {2013, 1, 1, ALIGNED_WEEK_OF_MONTH, 0},
                {2013, 1, 1, ALIGNED_WEEK_OF_MONTH, 5},
                {2012, 1, 1, ALIGNED_WEEK_OF_MONTH, 0},
                {2012, 1, 1, ALIGNED_WEEK_OF_MONTH, 5},
                {2013, 1, 1, ALIGNED_WEEK_OF_YEAR, 0},
                {2013, 1, 1, ALIGNED_WEEK_OF_YEAR, 53},
                {2012, 1, 1, ALIGNED_WEEK_OF_YEAR, 0},
                {2012, 1, 1, ALIGNED_WEEK_OF_YEAR, 53},

                // Normal dates use day-of-week values 1 through 7.
                {2013, 1, 1, DAY_OF_WEEK, 0},
                {2013, 1, 1, DAY_OF_WEEK, 8},
                {2012, 1, 1, DAY_OF_WEEK, 0},
                {2012, 1, 1, DAY_OF_WEEK, 8},

                // Day-of-month limits vary for regular months, leap day, and year day.
                {2013, 1, 1, DAY_OF_MONTH, -1},
                {2013, 1, 1, DAY_OF_MONTH, 29},
                {2013, 6, 1, DAY_OF_MONTH, 29},
                {2012, 6, 1, DAY_OF_MONTH, 30},
                {2012, 1, 1, DAY_OF_MONTH, -2},
                {2012, 1, 1, DAY_OF_MONTH, 29},
                {2013, 13, 1, DAY_OF_MONTH, 30},
                {2012, 13, 1, DAY_OF_MONTH, 30},

                // Day-of-year and epoch-day values must remain inside the chronology range.
                {2013, 1, 1, DAY_OF_YEAR, 0},
                {2012, 1, 1, DAY_OF_YEAR, 0},
                {2013, 1, 1, DAY_OF_YEAR, 366},
                {2012, 1, 1, DAY_OF_YEAR, 367},
                {2013, 1, 1, EPOCH_DAY, -719_529},
                {2013, 1, 1, EPOCH_DAY, 1_000_000 * 365L + 242_499 - 719_528 + 1},

                // Month and year fields are bounded by the supported calendar range.
                {2013, 1, 1, MONTH_OF_YEAR, -1},
                {2013, 1, 1, MONTH_OF_YEAR, 14},
                {2012, 1, 1, MONTH_OF_YEAR, -2},
                {2012, 1, 1, MONTH_OF_YEAR, 14},
                {2013, 1, 1, YEAR, 0},

                // Non-special leap days reject the special day and week value 0.
                {2012, 6, 21, DAY_OF_WEEK, 0},
                {2012, 6, 21, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0},
                {2012, 6, 21, ALIGNED_WEEK_OF_MONTH, 0},
                {2012, 6, 21, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0},
                {2012, 6, 21, ALIGNED_WEEK_OF_YEAR, 0},
        };
    }

    @ParameterizedTest
    @MethodSource("data_with_bad")
    public void test_with_TemporalField_badValue(int year, int month, int dom, TemporalField field, long value) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, month, dom).with(field, value));
    }
}
