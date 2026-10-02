package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that adding a chronology-specific {@link java.time.chrono.ChronoPeriod}
 * to an {@code AccountingDate} advances the date correctly, including rolling
 * over a month boundary.
 */
public class TestAccountingChronology_test_plus_Period {

    /**
     * Accounting calendar that ends on the Sunday nearest the end of August and
     * splits each year into thirteen even 4-week months, with the leap week in
     * month 13.
     */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_plus_Period() {
        // Each month in this calendar has 28 days, so adding 2 months and 3 days
        // to 2014-05-26 reaches 2014-07-29, which rolls over into 2014-08-01.
        AccountingDate start = INSTANCE.date(2014, 5, 26);
        AccountingDate expected = INSTANCE.date(2014, 8, 1);

        assertEquals(expected, start.plus(INSTANCE.period(0, 2, 3)));
    }
}
