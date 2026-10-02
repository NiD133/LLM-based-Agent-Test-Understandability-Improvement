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
public class TestSymmetry010Chronology_test_with_TemporalField_badValue {

    /**
     * Provides (year, month, day, field, value) tuples where calling
     * {@code Symmetry010Date.of(year, month, day).with(field, value)} must throw
     * {@link DateTimeException} because {@code value} is outside the valid range
     * for {@code field}.
     */
    public static Object[][] data_with_bad() {
        return new Object[][] {
            // ALIGNED_DAY_OF_WEEK_IN_MONTH: valid range 1-7
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, -1 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH,  8 },

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: valid range 1-7
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, -1 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR,  8 },

            // ALIGNED_WEEK_OF_MONTH: valid range depends on month length (max 4 or 5)
            { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH, -1 },
            { 2013, 2, 1, ALIGNED_WEEK_OF_MONTH,  6 }, // month 2 has at most 5 weeks

            // ALIGNED_WEEK_OF_YEAR: valid range 1-52 (or 53 in a leap year)
            { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR, -1 },
            { 2015, 1, 1, ALIGNED_WEEK_OF_YEAR, 54 }, // 2015 is a leap year with 53 weeks

            // DAY_OF_WEEK: valid range 1-7
            { 2013, 1, 1, DAY_OF_WEEK, -1 },
            { 2013, 1, 1, DAY_OF_WEEK,  8 },

            // DAY_OF_MONTH: valid range depends on month (30 or 31; 37 for Dec in leap year)
            { 2013, 1,  1, DAY_OF_MONTH, -1 },
            { 2013, 1,  1, DAY_OF_MONTH, 31 }, // month 1 has 30 days
            { 2013, 6,  1, DAY_OF_MONTH, 31 }, // month 6 has 30 days
            { 2013, 12, 1, DAY_OF_MONTH, 31 }, // month 12 has 30 days in non-leap year
            { 2015, 12, 1, DAY_OF_MONTH, 38 }, // month 12 has 37 days in leap year 2015

            // DAY_OF_YEAR: valid range 1-364 (or 1-371 in a leap year)
            { 2013, 1, 1, DAY_OF_YEAR,  -1 },
            { 2013, 1, 1, DAY_OF_YEAR, 365 }, // non-leap year has 364 days
            { 2015, 1, 1, DAY_OF_YEAR, 372 }, // leap year 2015 has 371 days

            // MONTH_OF_YEAR: valid range 1-12
            { 2013, 1, 1, MONTH_OF_YEAR,  -1 },
            { 2013, 1, 1, MONTH_OF_YEAR,  14 },
            { 2013, 1, 1, MONTH_OF_YEAR,  -2 },
            { 2015, 1, 1, MONTH_OF_YEAR,  14 },

            // EPOCH_DAY: values beyond the supported epoch-day range
            { 2013, 1, 1, EPOCH_DAY, -365_961_481 },
            { 2013, 1, 1, EPOCH_DAY,  364_523_156 },

            // YEAR: valid range -1_000_000 to 1_000_000
            { 2013, 1, 1, YEAR, -1_000_001 },
            { 2013, 1, 1, YEAR,  1_000_001 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with_bad")
    public void test_with_TemporalField_badValue(int year, int month, int dom, TemporalField field, long value) {
        assertThrows(DateTimeException.class, () -> Symmetry010Date.of(year, month, dom).with(field, value));
    }
}
