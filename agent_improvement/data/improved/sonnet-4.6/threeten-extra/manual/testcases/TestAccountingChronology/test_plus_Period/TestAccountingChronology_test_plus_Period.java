package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_plus_Period {

    // Accounting calendar: ends on Sunday nearest end of August,
    // divided into 13 even months of 4 weeks, with the leap week in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // Adding 0 years, 2 months, 3 days to 2014-05-26:
    //   month 5 + 2 = month 7, day 26 + 3 = day 29 → overflows into month 8, day 1.
    @Test
    public void test_plus_Period() {
        AccountingDate startDate = INSTANCE.date(2014, 5, 26);
        ChronoPeriod period = INSTANCE.period(0, 2, 3);
        AccountingDate expectedDate = INSTANCE.date(2014, 8, 1);

        assertEquals(expectedDate, startDate.plus(period));
    }
}
