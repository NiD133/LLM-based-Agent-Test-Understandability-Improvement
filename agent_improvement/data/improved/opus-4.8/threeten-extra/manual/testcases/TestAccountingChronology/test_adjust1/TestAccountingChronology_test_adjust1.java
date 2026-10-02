package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Verifies that applying the {@link TemporalAdjusters#lastDayOfMonth()} adjuster
 * to an {@link AccountingDate} moves the date to the final day of its accounting month.
 */
public class TestAccountingChronology_test_adjust1 {

    /**
     * Accounting calendar that ends on the Sunday nearest the end of August and splits
     * each year into thirteen 4-week months, with the leap week placed in month 13.
     */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_adjust1() {
        // Month 6 is a regular 4-week (28-day) accounting month, so its last day is day 28.
        AccountingDate dayWithinMonth = INSTANCE.date(2012, 6, 23);

        AccountingDate lastDayOfMonth = dayWithinMonth.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(INSTANCE.date(2012, 6, 28), lastDayOfMonth);
    }
}
