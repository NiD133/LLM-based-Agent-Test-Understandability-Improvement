package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that DiscordianChronology.dateYearDay produces consistent dates
 * whether called with a proleptic year or with an explicit era + year-of-era.
 */
public class TestDiscordianChronology_test_era_yearDay_loop {

    @Test
    public void test_era_yearDay_loop() {
        for (int year = 1; year < 200; year++) {
            // Create the first day of the year using only the proleptic year.
            DiscordianDate prolepticDate = DiscordianChronology.INSTANCE.dateYearDay(year, 1);

            // The proleptic year and year-of-era are identical in the YOLD era.
            assertEquals(year, prolepticDate.get(YEAR));
            assertEquals(DiscordianEra.YOLD, prolepticDate.getEra());
            assertEquals(year, prolepticDate.get(YEAR_OF_ERA));

            // The same date must be reachable by supplying the era explicitly.
            DiscordianDate eraBasedDate =
                    DiscordianChronology.INSTANCE.dateYearDay(DiscordianEra.YOLD, year, 1);
            assertEquals(prolepticDate, eraBasedDate);
        }
    }
}
