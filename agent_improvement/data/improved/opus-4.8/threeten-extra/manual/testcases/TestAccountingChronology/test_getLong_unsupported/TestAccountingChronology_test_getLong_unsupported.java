package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AccountingChronology} rejects temporal fields it does not support.
 */
public class TestAccountingChronology_test_getLong_unsupported {

    /** An accounting chronology whose year ends on the Sunday nearest the end of August. */
    private static final AccountingChronology ACCOUNTING_CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void getLong_withTimeBasedField_throwsUnsupported() {
        // MINUTE_OF_DAY is a time-of-day field; a date-only accounting date cannot supply it.
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> ACCOUNTING_CHRONOLOGY.date(2012, 6, 28).getLong(MINUTE_OF_DAY));
    }
}
