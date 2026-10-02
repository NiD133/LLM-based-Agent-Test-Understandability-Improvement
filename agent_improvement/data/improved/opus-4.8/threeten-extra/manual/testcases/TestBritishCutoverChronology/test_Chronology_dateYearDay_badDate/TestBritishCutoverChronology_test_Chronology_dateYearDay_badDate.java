package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BritishCutoverChronology#dateYearDay(int, int)} rejects an
 * out-of-range day-of-year.
 */
public class TestBritishCutoverChronology_test_Chronology_dateYearDay_badDate {

    /**
     * 2001 is a common (non-leap) year, so it only has 365 days. Asking for
     * day-of-year 366 is invalid and must raise a {@link DateTimeException}.
     */
    @Test
    public void test_Chronology_dateYearDay_badDate() {
        int nonLeapYear = 2001;
        int dayBeyondYearEnd = 366;

        assertThrows(DateTimeException.class,
                () -> BritishCutoverChronology.INSTANCE.dateYearDay(nonLeapYear, dayBeyondYearEnd));
    }
}
