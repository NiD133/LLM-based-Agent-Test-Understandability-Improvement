package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TemporalAdjusters#lastDayOfMonth()} moves an
 * {@link AccountingDate} to the final day of its accounting month.
 */
public class TestAccountingChronology_test_adjust2 {

    /**
     * Accounting calendar that ends on the Sunday nearest the end of August and
     * splits the year into thirteen 4-week months, with the leap week in month 13.
     */
    private static final AccountingChronology ACCOUNTING_CHRONOLOGY =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void lastDayOfMonth_movesToFinalDayOfAccountingMonth() {
        // Month 13 of a leap year (2012) runs 35 days because it holds the leap week.
        AccountingDate dateMidMonth = ACCOUNTING_CHRONOLOGY.date(2012, 13, 23);

        AccountingDate adjusted = dateMidMonth.with(TemporalAdjusters.lastDayOfMonth());

        AccountingDate expectedLastDay = ACCOUNTING_CHRONOLOGY.date(2012, 13, 35);
        assertEquals(expectedLastDay, adjusted);
    }
}
