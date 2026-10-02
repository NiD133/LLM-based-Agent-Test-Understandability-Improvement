package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry454Date#range(TemporalField)}, i.e. the valid value range
 * reported for a given field on a given date.
 * <p>
 * Symmetry454 facts that explain the expected ranges below:
 * <ul>
 * <li>Most months have 28 days (4 weeks); the "long" months February, May, August and November
 *     have 35 days (5 weeks). In a leap year December is also a long month.</li>
 * <li>A normal year has 364 days (52 weeks); a leap year has 371 days (53 weeks).
 *     Year 2012 is a normal year and year 2015 is a leap year.</li>
 * </ul>
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_range {

    /**
     * Each case is: year, month, day-of-month, field queried, expected value range.
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: 1..28 for normal months, 1..35 for the long months (and Dec in a leap year)
            { 2012, 1, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 2, 23, DAY_OF_MONTH, ValueRange.of(1, 35) },
            { 2012, 3, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 4, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 5, 23, DAY_OF_MONTH, ValueRange.of(1, 35) },
            { 2012, 6, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 7, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 8, 23, DAY_OF_MONTH, ValueRange.of(1, 35) },
            { 2012, 9, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 35) },
            { 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2015, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 35) }, // leap year: December is long

            // DAY_OF_WEEK: always 1..7
            { 2012, 1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },

            // DAY_OF_YEAR: 1..364 in a normal year, 1..371 in a leap year
            { 2012, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 364) },
            { 2015, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 371) },

            // MONTH_OF_YEAR: always 1..12
            { 2012, 1, 23, MONTH_OF_YEAR, ValueRange.of(1, 12) },

            // ALIGNED_DAY_OF_WEEK_IN_MONTH: always 1..7
            { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

            // ALIGNED_WEEK_OF_MONTH: 1..4 for normal months, 1..5 for long months
            { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012, 2, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 5) },
            { 2015, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 5) }, // leap year: December is long

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: always 1..7
            { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

            // ALIGNED_WEEK_OF_YEAR: 1..52 in a normal year, 1..53 in a leap year
            { 2012, 1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2015, 12, 30, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 53) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, ValueRange expectedRange) {
        Symmetry454Date date = Symmetry454Date.of(year, month, dom);
        assertEquals(expectedRange, date.range(field));
    }
}
