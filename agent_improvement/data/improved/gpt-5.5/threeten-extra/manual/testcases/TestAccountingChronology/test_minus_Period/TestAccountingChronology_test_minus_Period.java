package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_minus_Period {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_minus_Period() {
        AccountingDate expectedDate = INSTANCE.date(2014, 3, 23);
        AccountingDate dateToSubtractFrom = INSTANCE.date(2014, 5, 26);
        ChronoPeriod periodToSubtract = INSTANCE.period(0, 2, 3);

        assertEquals(expectedDate, dateToSubtractFrom.minus(periodToSubtract));
    }
}
