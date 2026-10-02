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
public class TestSymmetry454Chronology_test_with_TemporalField_badValue {

    /**
     * Returns (year, month, day, field, value) tuples where {@code value} is outside the
     * valid range for {@code field}, so {@code Symmetry454Date.of(year, month, day).with(field, value)}
     * must throw a {@link DateTimeException}.
     */
    public static Object[][] data_with_bad() {
        return new Object[][] {
            // ALIGNED_DAY_OF_WEEK_IN_MONTH: valid range [1, 7]
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, -1 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH,  8 },

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: valid range [1, 7]
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, -1 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR,  8 },

            // ALIGNED_WEEK_OF_MONTH: valid range [1, 4] for short months, [1, 5] for long months
            { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH, -1 },
            { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH,  5 }, // month 1 is short (max 4 weeks)
            { 2013, 2, 1, ALIGNED_WEEK_OF_MONTH,  6 }, // month 2 is long (max 5 weeks)

            // ALIGNED_WEEK_OF_YEAR: valid range [1, 52] in normal years, [1, 53] in leap years
            { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR, -1 },
            { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR, 53 }, // 2013 is not a leap year (max 52)
            { 2015, 1, 1, ALIGNED_WEEK_OF_YEAR, 54 }, // 2015 is a leap year (max 53)

            // DAY_OF_WEEK: valid range [1, 7]
            { 2013, 1, 1, DAY_OF_WEEK, -1 },
            { 2013, 1, 1, DAY_OF_WEEK,  8 },

            // DAY_OF_MONTH: valid range [1, 28] for short months, [1, 35] for long months
            { 2013, 1, 1, DAY_OF_MONTH,  -1 },
            { 2013, 1, 1, DAY_OF_MONTH,  29 }, // month 1 is short (max 28)
            { 2013, 6, 1, DAY_OF_MONTH,  29 }, // month 6 is short (max 28)
            { 2013, 12, 1, DAY_OF_MONTH, 30 }, // month 12 in a non-leap year is short (max 28)
            { 2015, 12, 1, DAY_OF_MONTH, 36 }, // month 12 in a leap year is long (max 35)

            // DAY_OF_YEAR: valid range [1, 364] in normal years, [1, 371] in leap years
            { 2013, 1, 1, DAY_OF_YEAR,  -1 },
            { 2013, 1, 1, DAY_OF_YEAR, 365 }, // 2013 is not a leap year (max 364)
            { 2015, 1, 1, DAY_OF_YEAR, 372 }, // 2015 is a leap year (max 371)

            // MONTH_OF_YEAR: valid range [1, 12]
            { 2013, 1, 1, MONTH_OF_YEAR,  -1 },
            { 2013, 1, 1, MONTH_OF_YEAR,  14 },
            { 2013, 1, 1, MONTH_OF_YEAR,  -2 },
            { 2013, 1, 1, MONTH_OF_YEAR,  14 },

            // EPOCH_DAY: valid range determined by the chronology's max/min year
            { 2013, 1, 1, EPOCH_DAY, -365_961_481 },
            { 2013, 1, 1, EPOCH_DAY,  364_523_156 },

            // YEAR: valid range [-1_000_000, 1_000_000]
            { 2013, 1, 1, YEAR, -1_000_001 },
            { 2013, 1, 1, YEAR,  1_000_001 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with_bad")
    public void test_with_TemporalField_badValue(int year, int month, int dom, TemporalField field, long value) {
        assertThrows(DateTimeException.class, () -> Symmetry454Date.of(year, month, dom).with(field, value));
    }
}
