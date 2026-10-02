package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianChronology#dateYearDay(int, int)} rejects an invalid day-of-year.
 */
public class TestJulianChronology_test_chronology_dateYearDay_badDate {

    /**
     * The year 2001 is not a Julian leap year, so it has only 365 days.
     * Requesting day-of-year 366 must therefore fail.
     */
    @Test
    public void dateYearDay_dayOfYearTooLargeForNonLeapYear_throws() {
        int nonLeapYear = 2001;
        int dayOfYearBeyondYearLength = 366;

        assertThrows(DateTimeException.class,
                () -> JulianChronology.INSTANCE.dateYearDay(nonLeapYear, dayOfYearBeyondYearLength));
    }
}
