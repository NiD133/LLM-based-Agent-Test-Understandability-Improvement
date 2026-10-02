package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_adjust_toLocalDate {

    private static final AccountingChronology ACCOUNTING_CHRONOLOGY =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void test_adjust_toLocalDate() {
        AccountingDate accountingDate = ACCOUNTING_CHRONOLOGY.date(2000, 1, 4);
        LocalDate isoAdjustmentDate = LocalDate.of(2012, 7, 6);

        AccountingDate adjustedDate = accountingDate.with(isoAdjustmentDate);

        assertEquals(ACCOUNTING_CHRONOLOGY.date(2012, 12, 5), adjustedDate);
    }
}
