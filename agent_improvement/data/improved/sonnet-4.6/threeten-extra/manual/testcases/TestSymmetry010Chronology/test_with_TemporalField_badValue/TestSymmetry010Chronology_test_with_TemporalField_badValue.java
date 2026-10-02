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
 * Tests that {@link Symmetry010Date#with(TemporalField, long)} throws
 * {@link DateTimeException} when an out-of-range value is supplied for
 * a supported temporal field.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_with_TemporalField_badValue {

    /**
     * Test cases: {year, month, dayOfMonth, field, outOfRangeValue}.
     *
     * <p>Each row represents a date and a temporal field set to a value that
     * lies outside the field's valid range, which must trigger a
     * {@link DateTimeException}.
     */
    public static Object[][] data_with_bad() {
        return new Object[][] {
            // ALIGNED_DAY_OF_WEEK_IN_MONTH: valid range [1..7]
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, -1 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH,  8 },

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: valid range [1..7]
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, -1 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR,  8 },

            // ALIGNED_WEEK_OF_MONTH: valid range [1..4] (1..5 in leap-week month)
            { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH, -1 },
            { 2013, 2, 1, ALIGNED_WEEK_OF_MONTH,  6 },

            // ALIGNED_WEEK_OF_YEAR: valid range [1..52] (1..53 in leap years)
            { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR, -1 },
            { 2015, 1, 1, ALIGNED_WEEK_OF_YEAR, 54 },

            // DAY_OF_WEEK: valid range [1..7]
            { 2013, 1, 1, DAY_OF_WEEK, -1 },
            { 2013, 1, 1, DAY_OF_WEEK,  8 },

            // DAY_OF_MONTH: valid range [1..30] for standard months, [1..37] for leap-week December
            { 2013,  1, 1, DAY_OF_MONTH, -1 },
            { 2013,  1, 1, DAY_OF_MONTH, 31 },
            { 2013,  6, 1, DAY_OF_MONTH, 31 },
            { 2013, 12, 1, DAY_OF_MONTH, 31 },
            { 2015, 12, 1, DAY_OF_MONTH, 38 },

            // DAY_OF_YEAR: valid range [1..364] (1..371 in leap years)
            { 2013, 1, 1, DAY_OF_YEAR,  -1 },
            { 2013, 1, 1, DAY_OF_YEAR, 365 },
            { 2015, 1, 1, DAY_OF_YEAR, 372 },

            // MONTH_OF_YEAR: valid range [1..12]
            { 2013, 1, 1, MONTH_OF_YEAR,  -1 },
            { 2013, 1, 1, MONTH_OF_YEAR,  14 },
            { 2013, 1, 1, MONTH_OF_YEAR,  -2 },
            { 2015, 1, 1, MONTH_OF_YEAR,  14 },

            // EPOCH_DAY: values beyond the calendar's supported range
            { 2013, 1, 1, EPOCH_DAY, -365_961_481 },
            { 2013, 1, 1, EPOCH_DAY,  364_523_156 },

            // YEAR: valid range [-1_000_000..1_000_000]
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
