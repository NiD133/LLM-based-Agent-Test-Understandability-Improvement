package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_adjust_toMonth {

    // Chronology: ends on Sunday nearest end of August, 13 equal 4-week months, leap week in month 13
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // AccountingDate.with(Month) is unsupported because Month is an ISO concept;
    // the Accounting calendar uses its own month numbering and does not accept ISO Month adjusters.
    @Test
    public void test_adjust_toMonth() {
        AccountingDate accounting = INSTANCE.date(2000, 1, 4);
        assertThrows(DateTimeException.class, () -> accounting.with(Month.APRIL));
    }
}
