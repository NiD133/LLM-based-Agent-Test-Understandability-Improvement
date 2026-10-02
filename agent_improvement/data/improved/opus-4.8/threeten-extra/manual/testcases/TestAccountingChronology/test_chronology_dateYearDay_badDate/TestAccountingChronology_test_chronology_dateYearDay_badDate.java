package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AccountingChronology#dateYearDay(int, int)} rejects a
 * day-of-year that does not exist in the given year.
 */
public class TestAccountingChronology_test_chronology_dateYearDay_badDate {

    private static final AccountingChronology ACCOUNTING_CHRONOLOGY =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void test_chronology_dateYearDay_badDate() {
        // Year 2001 is not a leap year, so it has only 364 days; day 366 is out of range.
        assertThrows(DateTimeException.class, () -> ACCOUNTING_CHRONOLOGY.dateYearDay(2001, 366));
    }
}
