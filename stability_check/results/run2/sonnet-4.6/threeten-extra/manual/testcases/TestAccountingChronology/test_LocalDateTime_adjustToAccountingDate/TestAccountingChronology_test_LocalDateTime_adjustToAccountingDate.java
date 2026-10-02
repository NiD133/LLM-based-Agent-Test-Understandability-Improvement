package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_LocalDateTime_adjustToAccountingDate {

    // Accounting calendar: ends on Sunday nearest end of August,
    // divided into 13 equal months of 4 weeks, with leap week in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_LocalDateTime_adjustToAccountingDate() {
        // Accounting date 2012-06-23 corresponds to ISO date 2012-02-07.
        // Adjusting LocalDateTime.MIN (time 00:00) to this accounting date
        // should yield LocalDateTime at the equivalent ISO date, preserving the time.
        AccountingDate accountingDate = INSTANCE.date(2012, 6, 23);
        LocalDateTime result = LocalDateTime.MIN.with(accountingDate);
        assertEquals(LocalDateTime.of(2012, 2, 7, 0, 0), result);
    }
}
