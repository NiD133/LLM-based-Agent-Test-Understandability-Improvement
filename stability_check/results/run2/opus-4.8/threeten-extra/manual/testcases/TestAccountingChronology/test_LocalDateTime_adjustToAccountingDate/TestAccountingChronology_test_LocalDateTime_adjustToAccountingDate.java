package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_LocalDateTime_adjustToAccountingDate {

    // A 13-month accounting calendar whose year ends on the Sunday nearest the end of August,
    // with the leap week placed in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_LocalDateTime_adjustToAccountingDate() {
        AccountingDate accountingDate = INSTANCE.date(2012, 6, 23);

        // Adjusting LocalDateTime.MIN with an accounting date replaces its date part
        // (keeping the MIN time-of-day, which is midnight) with the equivalent ISO date.
        LocalDateTime adjusted = LocalDateTime.MIN.with(accountingDate);

        assertEquals(LocalDateTime.of(2012, 2, 7, 0, 0), adjusted);
    }
}
