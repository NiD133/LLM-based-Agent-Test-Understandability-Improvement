package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianChronology#dateYearDay(int, int)} rejects a
 * day-of-year that is out of range for the given year.
 */
public class TestDiscordianChronology_test_chronology_dateYearDay_badDate {

    @Test
    public void dateYearDay_rejectsDayOfYearBeyondYearLength() {
        // The Discordian year 2001 is not a leap year, so day-of-year 366
        // does not exist and must be rejected.
        int nonLeapYear = 2001;
        int invalidDayOfYear = 366;

        assertThrows(DateTimeException.class,
                () -> DiscordianChronology.INSTANCE.dateYearDay(nonLeapYear, invalidDayOfYear));
    }
}
