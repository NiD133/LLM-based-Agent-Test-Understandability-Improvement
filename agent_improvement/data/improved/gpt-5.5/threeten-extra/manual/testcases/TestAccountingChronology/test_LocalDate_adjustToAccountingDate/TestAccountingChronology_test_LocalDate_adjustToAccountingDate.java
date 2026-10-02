package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_LocalDate_adjustToAccountingDate {

    private static final AccountingChronology ACCOUNTING_CHRONOLOGY =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void test_LocalDate_adjustToAccountingDate() {
        AccountingDate accountingDate = ACCOUNTING_CHRONOLOGY.date(2012, 6, 23);

        LocalDate adjustedDate = LocalDate.MIN.with(accountingDate);

        assertEquals(LocalDate.of(2012, 2, 7), adjustedDate);
    }
}
