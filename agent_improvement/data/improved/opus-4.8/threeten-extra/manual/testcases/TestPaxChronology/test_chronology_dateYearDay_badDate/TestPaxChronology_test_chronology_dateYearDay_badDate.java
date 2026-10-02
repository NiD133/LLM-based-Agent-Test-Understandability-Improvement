package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PaxChronology#dateYearDay(int, int)} rejects a
 * day-of-year that is out of range for the given Pax year.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_chronology_dateYearDay_badDate {

    @Test
    public void dateYearDay_withDayOfYearBeyondYearLength_throwsDateTimeException() {
        // Year 2001 is not a Pax leap year, so it has only 364 days;
        // requesting day 365 must be rejected.
        int nonLeapYear = 2001;
        int dayBeyondYearLength = 365;

        assertThrows(DateTimeException.class,
                () -> PaxChronology.INSTANCE.dateYearDay(nonLeapYear, dayBeyondYearLength));
    }
}
