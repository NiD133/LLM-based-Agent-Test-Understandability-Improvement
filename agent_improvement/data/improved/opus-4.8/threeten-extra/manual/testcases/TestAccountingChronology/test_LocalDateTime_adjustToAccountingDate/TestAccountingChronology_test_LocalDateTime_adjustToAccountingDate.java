package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Verifies that applying an {@link AccountingDate} as a {@code TemporalAdjuster}
 * to a {@link LocalDateTime} shifts the date to the equivalent ISO date while
 * leaving the time component untouched.
 */
public class TestAccountingChronology_test_LocalDateTime_adjustToAccountingDate {

    // Accounting calendar that ends on the Sunday nearest the end of August,
    // split into thirteen 4-week months, with the leap week in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_LocalDateTime_adjustToAccountingDate() {
        // Accounting date 2012-06-23 corresponds to ISO date 2012-02-07.
        AccountingDate accountingDate = INSTANCE.date(2012, 6, 23);

        // Adjusting LocalDateTime.MIN keeps its midnight time and adopts the ISO date.
        LocalDateTime adjusted = LocalDateTime.MIN.with(accountingDate);

        assertEquals(LocalDateTime.of(2012, 2, 7, 0, 0), adjusted);
    }
}
