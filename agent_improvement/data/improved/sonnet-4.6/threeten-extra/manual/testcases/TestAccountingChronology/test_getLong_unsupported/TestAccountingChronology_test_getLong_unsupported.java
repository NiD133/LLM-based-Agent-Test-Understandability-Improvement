package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_getLong_unsupported {

    // A concrete AccountingChronology: ends on Sunday nearest end of August,
    // divided into 13 even 4-week months, with the leap week in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_getLong_unsupported() {
        // MINUTE_OF_DAY is a time field; AccountingDate is a date-only type, so it must be rejected.
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> INSTANCE.date(2012, 6, 28).getLong(MINUTE_OF_DAY));
    }
}
