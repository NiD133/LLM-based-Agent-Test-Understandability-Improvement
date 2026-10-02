package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_minus_Period {

    // A 13-month accounting calendar that ends on the Sunday nearest the end of August,
    // with thirteen 4-week months and the leap week placed in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_minus_Period() {
        // Subtracting a period of 0 years, 2 months, and 3 days from 2014-05-26
        // should yield 2014-03-23.
        AccountingDate startDate = INSTANCE.date(2014, 5, 26);
        ChronoPeriod period = INSTANCE.period(0, 2, 3);
        AccountingDate expectedDate = INSTANCE.date(2014, 3, 23);

        assertEquals(expectedDate, startDate.minus(period));
    }
}
