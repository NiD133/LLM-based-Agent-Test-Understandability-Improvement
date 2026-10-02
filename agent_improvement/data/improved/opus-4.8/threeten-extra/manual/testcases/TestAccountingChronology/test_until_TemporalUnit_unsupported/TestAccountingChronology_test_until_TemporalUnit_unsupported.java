package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AccountingDate#until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)}
 * rejects time-based units, which an Accounting (date-only) calendar cannot measure.
 */
public class TestAccountingChronology_test_until_TemporalUnit_unsupported {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void until_withTimeBasedUnit_throwsUnsupportedTemporalType() {
        AccountingDate start = INSTANCE.date(2012, 6, 28);
        AccountingDate end = INSTANCE.date(2012, 7, 1);

        // MINUTES is a time-based unit and is not supported by a date-only chronology.
        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
