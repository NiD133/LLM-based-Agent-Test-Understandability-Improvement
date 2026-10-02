package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Verifies the valid value ranges that {@link Symmetry010Chronology} reports for
 * each supported temporal field.
 * <p>
 * A {@link ValueRange} is expressed either as {@code of(min, max)} for a fixed
 * range, or as {@code of(min, smallestMax, largestMax)} when the upper bound
 * varies (for example a leap month/week/year that has extra days).
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_range {

    private static ValueRange rangeOf(java.time.temporal.ChronoField field) {
        return Symmetry010Chronology.INSTANCE.range(field);
    }

    @Test
    public void test_Chronology_range() {
        // Weekday-based fields: always 7 days per week.
        assertEquals(ValueRange.of(1, 7), rangeOf(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(1, 7), rangeOf(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(1, 7), rangeOf(DAY_OF_WEEK));

        // Week-of-month: normally 4 weeks, up to 5 in the long (leap) month.
        assertEquals(ValueRange.of(1, 4, 5), rangeOf(ALIGNED_WEEK_OF_MONTH));
        // Week-of-year: normally 52 weeks, 53 in a leap year.
        assertEquals(ValueRange.of(1, 52, 53), rangeOf(ALIGNED_WEEK_OF_YEAR));

        // Day-of-month: normally up to 30, up to 37 in the long (leap) month.
        assertEquals(ValueRange.of(1, 30, 37), rangeOf(DAY_OF_MONTH));
        // Day-of-year: normally 364 days, 371 in a leap year.
        assertEquals(ValueRange.of(1, 364, 371), rangeOf(DAY_OF_YEAR));

        // Calendar structure fields.
        assertEquals(ValueRange.of(0, 1), rangeOf(ERA));
        assertEquals(ValueRange.of(1, 12), rangeOf(MONTH_OF_YEAR));

        // Year-based fields span +/- 1,000,000 proleptic years.
        assertEquals(ValueRange.of(-1_000_000L, 1_000_000), rangeOf(YEAR));
        assertEquals(ValueRange.of(-1_000_000, 1_000_000), rangeOf(YEAR_OF_ERA));
        assertEquals(ValueRange.of(-12_000_000L, 11_999_999L), rangeOf(PROLEPTIC_MONTH));

        // Epoch day: min/max epoch day over the supported year range, offset so
        // that Symmetry010 epoch aligns with the ISO epoch (1970-01-01).
        assertEquals(
                ValueRange.of(
                        -1_000_000 * 364L - 177_474 * 7 - 719_162,
                        1_000_000 * 364L + 177_474 * 7 - 719_162),
                rangeOf(EPOCH_DAY));
    }
}
