package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianChronology#dateYearDay} produces consistent dates
 * whether or not an era is supplied.
 */
public class TestDiscordianChronology_test_era_yearDay_loop {

    /**
     * For every year in a representative range, a date built from year and
     * day-of-year should report that same year (both as proleptic year and as
     * year-of-era) and the single Discordian era, YOLD. Building the same date
     * with the era passed explicitly must yield an equal date.
     */
    @Test
    public void test_era_yearDay_loop() {
        for (int year = 1; year < 200; year++) {
            DiscordianDate dateWithoutEra = DiscordianChronology.INSTANCE.dateYearDay(year, 1);

            assertEquals(year, dateWithoutEra.get(YEAR));
            assertEquals(DiscordianEra.YOLD, dateWithoutEra.getEra());
            assertEquals(year, dateWithoutEra.get(YEAR_OF_ERA));

            DiscordianDate dateWithEra =
                    DiscordianChronology.INSTANCE.dateYearDay(DiscordianEra.YOLD, year, 1);
            assertEquals(dateWithoutEra, dateWithEra);
        }
    }
}
