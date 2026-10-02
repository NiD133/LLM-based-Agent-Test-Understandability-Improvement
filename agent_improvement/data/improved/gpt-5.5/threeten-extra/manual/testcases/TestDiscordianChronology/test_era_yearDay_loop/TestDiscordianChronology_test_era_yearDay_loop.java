package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_era_yearDay_loop {

    private static final int FIRST_SUPPORTED_YEAR = 1;
    private static final int EXCLUSIVE_UPPER_YEAR = 200;
    private static final int FIRST_DAY_OF_YEAR = 1;

    @Test
    public void test_era_yearDay_loop() {
        for (int year = FIRST_SUPPORTED_YEAR; year < EXCLUSIVE_UPPER_YEAR; year++) {
            DiscordianDate base = DiscordianChronology.INSTANCE.dateYearDay(year, FIRST_DAY_OF_YEAR);

            assertEquals(year, base.get(YEAR));
            assertEquals(DiscordianEra.YOLD, base.getEra());
            assertEquals(year, base.get(YEAR_OF_ERA));

            DiscordianDate eraBased = DiscordianChronology.INSTANCE.dateYearDay(
                    DiscordianEra.YOLD,
                    year,
                    FIRST_DAY_OF_YEAR);
            assertEquals(base, eraBased);
        }
    }
}
