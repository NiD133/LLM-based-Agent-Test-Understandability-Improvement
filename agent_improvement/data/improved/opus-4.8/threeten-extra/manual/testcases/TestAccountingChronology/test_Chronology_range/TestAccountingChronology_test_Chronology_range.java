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
 * Verifies the value ranges that {@link AccountingChronology#range(java.time.temporal.ChronoField)}
 * reports for the supported date fields.
 *
 * <p>The chronology under test uses a 13-month layout (each month is 4 weeks long) whose leap week
 * falls in month 13, so the expected ranges reflect that calendar shape:</p>
 * <ul>
 *   <li>days per month: a fixed 28, except month 13 which grows to 35 in a leap year;</li>
 *   <li>days per year: 364 normally, 371 in a leap year;</li>
 *   <li>weeks per year: 52 normally, 53 in a leap year.</li>
 * </ul>
 */
public class TestAccountingChronology_test_Chronology_range {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_Chronology_range() {
        // Day-of-week is always Monday(1) .. Sunday(7).
        assertEquals(ValueRange.of(1, 7), INSTANCE.range(DAY_OF_WEEK));
        // Day-of-month: 1..28 usually, but up to 35 in the leap-week month.
        assertEquals(ValueRange.of(1, 28, 35), INSTANCE.range(DAY_OF_MONTH));
        // Day-of-year: 1..364 usually, but up to 371 in a leap year.
        assertEquals(ValueRange.of(1, 364, 371), INSTANCE.range(DAY_OF_YEAR));
        // Month-of-year is always 1..13.
        assertEquals(ValueRange.of(1, 13), INSTANCE.range(MONTH_OF_YEAR));
        // Week-of-year: 1..52 usually, but up to 53 in a leap year.
        assertEquals(ValueRange.of(1, 52, 53), INSTANCE.range(ALIGNED_WEEK_OF_YEAR));
    }
}
