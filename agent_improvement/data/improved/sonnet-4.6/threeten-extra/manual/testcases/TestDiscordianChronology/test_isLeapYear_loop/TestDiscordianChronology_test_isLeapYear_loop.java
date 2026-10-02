package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntPredicate;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_isLeapYear_loop {

    // Covers 500 Discordian years spanning multiple Gregorian century boundaries
    // (year 1066 = ISO -100, year 1566 = ISO 400), ensuring both the divisible-by-100
    // exception and the divisible-by-400 override are exercised.
    private static final int YEAR_RANGE_START = 1066;
    private static final int YEAR_RANGE_END   = 1567;

    @Test
    public void test_isLeapYear_loop() {
        // A year is a Discordian leap year when its ISO equivalent (year - OFFSET) is a Gregorian leap year.
        IntPredicate isLeapYear = year -> {
            int offsetYear = year - DiscordianChronology.OFFSET_FROM_ISO_0000;
            return offsetYear % 4 == 0 && (offsetYear % 400 == 0 || offsetYear % 100 != 0);
        };

        for (int year = YEAR_RANGE_START; year < YEAR_RANGE_END; year++) {
            DiscordianDate base = DiscordianDate.of(year, 1, 1);
            assertEquals(isLeapYear.test(year), base.isLeapYear());
            assertEquals(isLeapYear.test(year), DiscordianChronology.INSTANCE.isLeapYear(year));
        }
    }
}
