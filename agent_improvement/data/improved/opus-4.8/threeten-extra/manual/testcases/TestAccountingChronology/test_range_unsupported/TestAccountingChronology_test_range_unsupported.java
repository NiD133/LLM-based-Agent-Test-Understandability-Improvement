package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that querying the value range of a time-based field on an accounting
 * date is rejected, since an accounting date carries only date information.
 */
public class TestAccountingChronology_test_range_unsupported {

    /** A 13-month accounting chronology whose year ends on the Sunday nearest the end of August. */
    private static final AccountingChronology ACCOUNTING_CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void range_forTimeBasedField_throwsUnsupportedTemporalType() {
        // MINUTE_OF_DAY is a time field; an accounting date has no time component,
        // so requesting its range must be unsupported.
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> ACCOUNTING_CHRONOLOGY.date(2012, 6, 28).range(MINUTE_OF_DAY));
    }
}
