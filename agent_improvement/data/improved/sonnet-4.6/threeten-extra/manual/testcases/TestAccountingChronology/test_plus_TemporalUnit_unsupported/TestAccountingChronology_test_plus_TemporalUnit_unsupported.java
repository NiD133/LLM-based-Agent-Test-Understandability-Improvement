package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that AccountingDate.plus() throws UnsupportedTemporalTypeException
 * when given a time-based unit (MINUTES) that the date-only type cannot handle.
 */
public class TestAccountingChronology_test_plus_TemporalUnit_unsupported {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_plus_TemporalUnit_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> INSTANCE.date(2012, 6, 28).plus(0, MINUTES));
    }
}
