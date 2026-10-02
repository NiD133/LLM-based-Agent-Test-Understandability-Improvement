package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AccountingChronology#range(java.time.temporal.ChronoField)} returns
 * correct value ranges for an accounting calendar configured with:
 * - year ending on SUNDAY nearest the end of AUGUST
 * - 13 equal months of 4 weeks each
 * - leap week appended to month 13
 */
public class TestAccountingChronology_test_Chronology_range {

    // A 13-month accounting chronology whose leap week falls in month 13.
    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void test_Chronology_range() {
        // There are always 7 days in a week regardless of the calendar variant.
        assertEquals(ValueRange.of(1, 7), INSTANCE.range(DAY_OF_WEEK));

        // Regular months have 28 days (4 weeks × 7); the leap-week month can reach 35.
        assertEquals(ValueRange.of(1, 28, 35), INSTANCE.range(DAY_OF_MONTH));

        // A standard accounting year has 364 days; a leap year adds one week → 371.
        assertEquals(ValueRange.of(1, 364, 371), INSTANCE.range(DAY_OF_YEAR));

        // This configuration always has exactly 13 months.
        assertEquals(ValueRange.of(1, 13), INSTANCE.range(MONTH_OF_YEAR));

        // A 52-week year has 52 aligned weeks; a 53-week leap year has one more.
        assertEquals(ValueRange.of(1, 52, 53), INSTANCE.range(ALIGNED_WEEK_OF_YEAR));
    }
}
