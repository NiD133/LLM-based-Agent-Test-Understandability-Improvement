package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_plus_Period {

    private static final AccountingChronology ACCOUNTING_CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_plus_Period() {
        AccountingDate startDate = ACCOUNTING_CHRONOLOGY.date(2014, 5, 26);
        ChronoPeriod periodToAdd = ACCOUNTING_CHRONOLOGY.period(0, 2, 3);
        AccountingDate expectedDate = ACCOUNTING_CHRONOLOGY.date(2014, 8, 1);

        assertEquals(expectedDate, startDate.plus(periodToAdd));
    }
}
