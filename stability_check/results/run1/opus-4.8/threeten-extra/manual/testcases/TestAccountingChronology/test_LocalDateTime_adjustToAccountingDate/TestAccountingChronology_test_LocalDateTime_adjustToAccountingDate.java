package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_LocalDateTime_adjustToAccountingDate {

    // An accounting calendar whose years end on the Sunday nearest the end of August,
    // split into 13 four-week months, with the leap week added to month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_LocalDateTime_adjustToAccountingDate() {
        // Adjusting a LocalDateTime with an AccountingDate should move its date part
        // to the equivalent ISO date, while leaving the time part (from LocalDateTime.MIN) untouched.
        AccountingDate accountingDate = INSTANCE.date(2012, 6, 23);

        LocalDateTime adjusted = LocalDateTime.MIN.with(accountingDate);

        assertEquals(LocalDateTime.of(2012, 2, 7, 0, 0), adjusted);
    }
}
