package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_LocalDateTime_adjustToAccountingDate {

    // Accounting calendar: ends on nearest Sunday to end of August,
    // 13 equal 4-week periods with the leap week in period 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // Verifies that adjusting LocalDateTime.MIN to an AccountingDate replaces
    // the date portion with the ISO equivalent of the given accounting date
    // while preserving the time component (midnight).
    @Test
    public void test_LocalDateTime_adjustToAccountingDate() {
        AccountingDate accounting = INSTANCE.date(2012, 6, 23);
        LocalDateTime adjustedDateTime = LocalDateTime.MIN.with(accounting);
        assertEquals(LocalDateTime.of(2012, 2, 7, 0, 0), adjustedDateTime);
    }
}
