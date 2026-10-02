package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link LocalDate} can be adjusted onto an {@link AccountingDate}
 * via {@link LocalDate#with(java.time.temporal.TemporalAdjuster)}.
 *
 * <p>The accounting calendar under test ends on the SUNDAY nearest the end of AUGUST,
 * divides the year into thirteen even 4-week months, and places the leap week in month 13.
 */
public class TestAccountingChronology_test_LocalDate_adjustToAccountingDate {

    private static final AccountingChronology ACCOUNTING_CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_LocalDate_adjustToAccountingDate() {
        // Accounting date: year 2012, month 6, day 23.
        AccountingDate accountingDate = ACCOUNTING_CHRONOLOGY.date(2012, 6, 23);

        // Adjusting any LocalDate onto an accounting date yields that date's ISO equivalent.
        LocalDate adjustedIsoDate = LocalDate.MIN.with(accountingDate);

        assertEquals(LocalDate.of(2012, 2, 7), adjustedIsoDate);
    }
}
