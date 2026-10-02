package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_until_TemporalUnit_unsupported {

    // A standard accounting chronology: ends on Sunday nearest end of August,
    // divided into 13 even 4-week months, with the leap week in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_until_TemporalUnit_unsupported() {
        AccountingDate start = INSTANCE.date(2012, 6, 28);
        AccountingDate end = INSTANCE.date(2012, 7, 1);
        // MINUTES is a time-based unit, which AccountingDate (a date-only type) does not support.
        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
