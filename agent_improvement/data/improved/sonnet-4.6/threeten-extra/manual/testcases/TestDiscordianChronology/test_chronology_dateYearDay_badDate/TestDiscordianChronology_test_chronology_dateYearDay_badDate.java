package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_chronology_dateYearDay_badDate {

    // Discordian year 2001 maps to ISO year 835 (2001 - 1166), which is not a leap year.
    // A non-leap year has only 365 days, so day-of-year 366 is invalid and must throw.
    @Test
    public void test_chronology_dateYearDay_badDate() {
        assertThrows(DateTimeException.class, () -> DiscordianChronology.INSTANCE.dateYearDay(2001, 366));
    }
}
