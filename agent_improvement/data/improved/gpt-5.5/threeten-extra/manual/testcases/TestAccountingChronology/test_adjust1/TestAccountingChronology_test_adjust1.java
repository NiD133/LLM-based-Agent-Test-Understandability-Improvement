package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_adjust1 {

    private static final AccountingChronology ACCOUNTING_CHRONOLOGY =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void test_adjust1() {
        AccountingDate baseDate = ACCOUNTING_CHRONOLOGY.date(2012, 6, 23);

        AccountingDate adjustedDate = baseDate.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(ACCOUNTING_CHRONOLOGY.date(2012, 6, 28), adjustedDate);
    }
}
