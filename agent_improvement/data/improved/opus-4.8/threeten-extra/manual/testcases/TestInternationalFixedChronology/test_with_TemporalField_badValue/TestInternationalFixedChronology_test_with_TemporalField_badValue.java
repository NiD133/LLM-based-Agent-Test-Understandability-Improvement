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

/**
 * Verifies that {@link InternationalFixedDate#with(TemporalField, long)} rejects
 * values that fall outside the valid range for the given field.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_with_TemporalField_badValue {

    /**
     * Each row is: year, month, dom (a valid starting date), then the field and
     * the out-of-range value that {@code with(field, value)} must reject.
     */
    public static Object[][] data_with_bad() {
        return new Object[][] {
            // ALIGNED_DAY_OF_WEEK_IN_MONTH: valid range is 1..7
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 8 },
            { 2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0 },
            { 2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 8 },
            // ALIGNED_DAY_OF_WEEK_IN_YEAR: valid range is 1..7
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 8 },
            { 2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0 },
            { 2012, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 8 },
            // ALIGNED_WEEK_OF_MONTH: valid range is 1..4
            { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH, 0 },
            { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH, 5 },
            { 2012, 1, 1, ALIGNED_WEEK_OF_MONTH, 0 },
            { 2012, 1, 1, ALIGNED_WEEK_OF_MONTH, 5 },
            // ALIGNED_WEEK_OF_YEAR: valid range is 1..52
            { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR, 0 },
            { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR, 53 },
            { 2012, 1, 1, ALIGNED_WEEK_OF_YEAR, 0 },
            { 2012, 1, 1, ALIGNED_WEEK_OF_YEAR, 53 },
            // DAY_OF_WEEK: valid range is 1..7
            { 2013, 1, 1, DAY_OF_WEEK, 0 },
            { 2013, 1, 1, DAY_OF_WEEK, 8 },
            { 2012, 1, 1, DAY_OF_WEEK, 0 },
            { 2012, 1, 1, DAY_OF_WEEK, 8 },
            // DAY_OF_MONTH: 28 days in a normal month, 29 in months 6 (leap year) and 13
            { 2013, 1, 1, DAY_OF_MONTH, -1 },
            { 2013, 1, 1, DAY_OF_MONTH, 29 },
            { 2013, 6, 1, DAY_OF_MONTH, 29 },
            { 2012, 6, 1, DAY_OF_MONTH, 30 },
            { 2012, 1, 1, DAY_OF_MONTH, -2 },
            { 2012, 1, 1, DAY_OF_MONTH, 29 },
            { 2013, 13, 1, DAY_OF_MONTH, 30 },
            { 2012, 13, 1, DAY_OF_MONTH, 30 },
            // DAY_OF_YEAR: 365 days in a normal year, 366 in a leap year
            { 2013, 1, 1, DAY_OF_YEAR, 0 },
            { 2012, 1, 1, DAY_OF_YEAR, 0 },
            { 2013, 1, 1, DAY_OF_YEAR, 366 },
            { 2012, 1, 1, DAY_OF_YEAR, 367 },
            // EPOCH_DAY: outside the supported epoch-day range
            { 2013, 1, 1, EPOCH_DAY, -719_529 },
            { 2013, 1, 1, EPOCH_DAY, 1_000_000 * 365L + 242_499 - 719_528 + 1 },
            // MONTH_OF_YEAR: valid range is 1..13
            { 2013, 1, 1, MONTH_OF_YEAR, -1 },
            { 2013, 1, 1, MONTH_OF_YEAR, 14 },
            { 2012, 1, 1, MONTH_OF_YEAR, -2 },
            { 2012, 1, 1, MONTH_OF_YEAR, 14 },
            // YEAR: year 0 is not a valid proleptic year
            { 2013, 1, 1, YEAR, 0 },
            // Leap Day (2012-06-29) has no aligned week/day fields, so any value is rejected
            { 2012, 6, 21, DAY_OF_WEEK, 0 },
            { 2012, 6, 21, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0 },
            { 2012, 6, 21, ALIGNED_WEEK_OF_MONTH, 0 },
            { 2012, 6, 21, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0 },
            { 2012, 6, 21, ALIGNED_WEEK_OF_YEAR, 0 }
        };
    }

    @ParameterizedTest
    @MethodSource("data_with_bad")
    public void test_with_TemporalField_badValue(int year, int month, int dom, TemporalField field, long value) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, month, dom).with(field, value));
    }
}
