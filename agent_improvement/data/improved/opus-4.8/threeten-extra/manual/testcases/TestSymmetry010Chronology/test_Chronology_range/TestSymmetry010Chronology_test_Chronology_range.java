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
 * Verifies the value ranges that {@link Symmetry010Chronology#range(java.time.temporal.ChronoField)}
 * reports for each supported field.
 * <p>
 * In the Symmetry010 calendar a normal year has 364 days (52 weeks) while a leap year adds an extra
 * leap week, giving 371 days; that leap week lands in December, stretching it to 37 days. Several
 * ranges below therefore carry both a "smallest maximum" (normal year) and a "largest maximum"
 * (leap year) via {@link ValueRange#of(long, long, long)}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        // Week-based fields: always a fixed 7-day week.
        assertEquals(ValueRange.of(1, 7), range(DAY_OF_WEEK));
        assertEquals(ValueRange.of(1, 7), range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(1, 7), range(ALIGNED_DAY_OF_WEEK_IN_YEAR));

        // Week counts: 4 weeks per normal month, 5 in the long (leap) December;
        // 52 weeks per normal year, 53 in a leap year.
        assertEquals(ValueRange.of(1, 4, 5), range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(1, 52, 53), range(ALIGNED_WEEK_OF_YEAR));

        // Day-of-month: up to 30 normally, up to 37 in a leap-year December.
        assertEquals(ValueRange.of(1, 30, 37), range(DAY_OF_MONTH));
        // Day-of-year: 364 in a normal year, 371 in a leap year.
        assertEquals(ValueRange.of(1, 364, 371), range(DAY_OF_YEAR));

        // Month and era are constant across years.
        assertEquals(ValueRange.of(1, 12), range(MONTH_OF_YEAR));
        assertEquals(ValueRange.of(0, 1), range(ERA));

        // Year-based fields are bounded by the chronology's +/- 1,000,000 year limit.
        assertEquals(ValueRange.of(-1_000_000L, 1_000_000), range(YEAR));
        assertEquals(ValueRange.of(-1_000_000, 1_000_000), range(YEAR_OF_ERA));
        assertEquals(ValueRange.of(-12_000_000L, 11_999_999L), range(PROLEPTIC_MONTH));

        // Epoch-day bounds derive from the year limit: MAX_YEAR * 364 days, plus the leap weeks
        // accumulated over that span (177,474 * 7 days), offset by the days from year 0001 to 1970.
        assertEquals(
                ValueRange.of(
                        -1_000_000 * 364L - 177_474 * 7 - 719_162,
                        1_000_000 * 364L + 177_474 * 7 - 719_162),
                range(EPOCH_DAY));
    }

    private static ValueRange range(java.time.temporal.ChronoField field) {
        return Symmetry010Chronology.INSTANCE.range(field);
    }
}
