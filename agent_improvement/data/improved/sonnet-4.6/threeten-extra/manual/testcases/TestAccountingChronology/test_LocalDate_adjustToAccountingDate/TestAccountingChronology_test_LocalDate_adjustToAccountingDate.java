package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_LocalDate_adjustToAccountingDate {

    // Chronology: year ends on the Sunday nearest the end of August,
    // divided into 13 equal 4-week months, with the leap week appended to month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // Verifies that a LocalDate adjusted to an AccountingDate produces the correct ISO date.
    // AccountingDate 2012-06-23 maps to ISO 2012-02-07; LocalDate.MIN.with(accounting)
    // returns the ISO equivalent of that accounting date.
    @Test
    public void test_LocalDate_adjustToAccountingDate() {
        AccountingDate accounting = INSTANCE.date(2012, 6, 23);
        LocalDate result = LocalDate.MIN.with(accounting);
        assertEquals(LocalDate.of(2012, 2, 7), result);
    }
}
