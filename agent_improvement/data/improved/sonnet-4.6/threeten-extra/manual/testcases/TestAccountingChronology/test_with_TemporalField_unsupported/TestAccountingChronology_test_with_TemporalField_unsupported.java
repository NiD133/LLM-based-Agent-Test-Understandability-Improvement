package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_with_TemporalField_unsupported {

    // A concrete chronology: ends on Sunday nearest end of August,
    // 13 equal 4-week months, leap week appended to month 13.
    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    // MINUTE_OF_DAY is a time-based field and has no meaning on a date-only value;
    // calling with() using it must throw UnsupportedTemporalTypeException.
    @Test
    public void test_with_TemporalField_unsupported() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> INSTANCE.date(2012, 6, 28).with(MINUTE_OF_DAY, 0));
    }
}
