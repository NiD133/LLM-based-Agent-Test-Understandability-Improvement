package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_era_loop {

    @Test
    public void test_era_loop() {
        for (int year = 1; year < 200; year++) {
            assertEraConsistencyForYear(year);
        }
    }

    private void assertEraConsistencyForYear(int year) {
        DiscordianDate base = DiscordianChronology.INSTANCE.date(year, 1, 1);
        assertEquals(year, base.get(YEAR));
        assertEquals(DiscordianEra.YOLD, base.getEra());
        assertEquals(year, base.get(YEAR_OF_ERA));
        DiscordianDate eraBased = DiscordianChronology.INSTANCE.date(DiscordianEra.YOLD, year, 1, 1);
        assertEquals(base, eraBased);
    }
}
