package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AccountingChronology}'s date type rejects time-based
 * fields. An accounting date has no time component, so adjusting it with a
 * {@code MINUTE_OF_DAY} field must fail.
 */
public class TestAccountingChronology_test_with_TemporalField_unsupported {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_with_TemporalField_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> INSTANCE.date(2012, 6, 28).with(MINUTE_OF_DAY, 0));
    }
}
