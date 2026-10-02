package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that calling {@code with()} on an {@link AccountingDate} with an unsupported
 * {@link java.time.temporal.TemporalField} (such as a time-based field) throws
 * {@link UnsupportedTemporalTypeException}, since AccountingDate is a date-only type.
 */
public class TestAccountingChronology_test_with_TemporalField_unsupported {

    /** A representative AccountingChronology instance used across tests. */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_with_TemporalField_unsupported() {
        // MINUTE_OF_DAY is a time-based field unsupported by a date-only AccountingDate
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> INSTANCE.date(2012, 6, 28).with(MINUTE_OF_DAY, 0));
    }
}
