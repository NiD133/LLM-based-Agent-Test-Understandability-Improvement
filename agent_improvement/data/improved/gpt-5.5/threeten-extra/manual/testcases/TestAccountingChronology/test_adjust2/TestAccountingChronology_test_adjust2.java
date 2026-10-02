package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_adjust2 {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_adjust2() {
        AccountingDate base = INSTANCE.date(2012, 13, 23);

        AccountingDate test = base.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(INSTANCE.date(2012, 13, 35), test);
    }
}
