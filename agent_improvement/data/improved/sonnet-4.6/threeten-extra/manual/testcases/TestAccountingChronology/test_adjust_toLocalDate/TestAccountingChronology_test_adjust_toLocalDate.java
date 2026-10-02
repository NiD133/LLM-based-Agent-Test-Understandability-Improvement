package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_adjust_toLocalDate {

    // Accounting calendar: ends on Sunday nearest end of August,
    // divided into 13 even months of 4 weeks, leap-week in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // Verifies that adjusting an AccountingDate with an ISO LocalDate
    // returns the AccountingDate that corresponds to that ISO date.
    // ISO 2012-07-06 maps to Accounting year 2012, month 12, day 5.
    @Test
    public void test_adjust_toLocalDate() {
        AccountingDate startDate = INSTANCE.date(2000, 1, 4);
        AccountingDate adjusted = startDate.with(LocalDate.of(2012, 7, 6));
        assertEquals(INSTANCE.date(2012, 12, 5), adjusted);
    }
}
