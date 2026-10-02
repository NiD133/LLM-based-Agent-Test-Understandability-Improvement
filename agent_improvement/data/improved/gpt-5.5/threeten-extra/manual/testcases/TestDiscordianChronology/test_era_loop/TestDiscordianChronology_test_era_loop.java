package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_era_loop {

    private static final int FIRST_SUPPORTED_YEAR = 1;
    private static final int FIRST_YEAR_NOT_TESTED = 200;
    private static final int FIRST_MONTH = 1;
    private static final int FIRST_DAY_OF_MONTH = 1;

    @Test
    public void test_era_loop() {
        for (int year = FIRST_SUPPORTED_YEAR; year < FIRST_YEAR_NOT_TESTED; year++) {
            DiscordianDate prolepticDate = DiscordianChronology.INSTANCE.date(
                    year,
                    FIRST_MONTH,
                    FIRST_DAY_OF_MONTH);

            assertEquals(year, prolepticDate.get(YEAR));
            assertEquals(DiscordianEra.YOLD, prolepticDate.getEra());
            assertEquals(year, prolepticDate.get(YEAR_OF_ERA));

            DiscordianDate eraDate = DiscordianChronology.INSTANCE.date(
                    DiscordianEra.YOLD,
                    year,
                    FIRST_MONTH,
                    FIRST_DAY_OF_MONTH);
            assertEquals(prolepticDate, eraDate);
        }
    }
}
