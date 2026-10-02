package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_chronology_dateYearDay_badDate {

    /**
     * Year 2001 is not a leap year, so it has only 365 days. Requesting day-of-year
     * 366 must be rejected with a {@link DateTimeException}.
     */
    @Test
    public void test_chronology_dateYearDay_badDate() {
        int nonLeapYear = 2001;
        int dayBeyondYearLength = 366;

        assertThrows(DateTimeException.class,
            () -> InternationalFixedChronology.INSTANCE.dateYearDay(nonLeapYear, dayBeyondYearLength));
    }
}
