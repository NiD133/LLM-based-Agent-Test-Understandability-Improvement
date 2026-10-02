package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AccountingChronology#dateYearDay(int, int)} rejects a
 * day-of-year that exceeds the length of the given accounting year.
 *
 * <p>The chronology under test ends on SUNDAY nearest end of AUGUST, divides
 * the year into thirteen 4-week months, and places the leap-week in month 13.
 * A standard (non-leap) accounting year contains exactly 364 days, so day 366
 * is always invalid for a non-leap year such as 2001.
 */
public class TestAccountingChronology_test_chronology_dateYearDay_badDate {

    /**
     * Accounting chronology whose standard year has 364 days (leap year: 371).
     * Year 2001 is not a leap year in this chronology, so its day-of-year range
     * is 1–364.
     */
    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void test_chronology_dateYearDay_badDate() {
        // Day 366 is out of range for accounting year 2001 (a 364-day year).
        assertThrows(DateTimeException.class, () -> INSTANCE.dateYearDay(2001, 366));
    }
}
