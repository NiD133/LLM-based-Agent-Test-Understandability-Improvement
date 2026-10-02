package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_adjust2 {

    // An accounting calendar that ends on the Sunday nearest to the end of August,
    // divided into 13 equal 4-week periods, with the leap week appended to period 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_adjust2() {
        // In a leap year (2012), period 13 has 35 days instead of the usual 28.
        // Applying lastDayOfMonth() to day 23 should return day 35.
        AccountingDate base = INSTANCE.date(2012, 13, 23);
        AccountingDate test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(INSTANCE.date(2012, 13, 35), test);
    }
}
