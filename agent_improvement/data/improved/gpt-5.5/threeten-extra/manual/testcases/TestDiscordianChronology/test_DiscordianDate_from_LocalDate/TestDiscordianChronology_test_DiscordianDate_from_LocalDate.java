package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_DiscordianDate_from_LocalDate {

    public static Object[][] data_samples() {
        return new Object[][] {
                // Discordian year starts at ISO January 1 with a 1166-year offset.
                {DiscordianDate.of(2, 1, 1), LocalDate.of(-1164, 1, 1)},
                {DiscordianDate.of(166, 1, 1), LocalDate.of(-1000, 1, 1)},
                {DiscordianDate.of(1156, 1, 1), LocalDate.of(-10, 1, 1)},
                {DiscordianDate.of(1166, 1, 1), LocalDate.of(0, 1, 1)},
                {DiscordianDate.of(1167, 1, 1), LocalDate.of(1, 1, 1)},
                {DiscordianDate.of(1167, 1, 2), LocalDate.of(1, 1, 2)},
                {DiscordianDate.of(1167, 1, 3), LocalDate.of(1, 1, 3)},

                // Non-leap and leap-year behavior around the end of February.
                {DiscordianDate.of(1167, 1, 57), LocalDate.of(1, 2, 26)},
                {DiscordianDate.of(1167, 1, 58), LocalDate.of(1, 2, 27)},
                {DiscordianDate.of(1167, 1, 59), LocalDate.of(1, 2, 28)},
                {DiscordianDate.of(1167, 1, 60), LocalDate.of(1, 3, 1)},
                {DiscordianDate.of(1170, 1, 57), LocalDate.of(4, 2, 26)},
                {DiscordianDate.of(1170, 1, 58), LocalDate.of(4, 2, 27)},
                {DiscordianDate.of(1170, 1, 59), LocalDate.of(4, 2, 28)},
                {DiscordianDate.of(1170, 0, 0), LocalDate.of(4, 2, 29)},
                {DiscordianDate.of(1170, 1, 60), LocalDate.of(4, 3, 1)},
                {DiscordianDate.of(1266, 1, 57), LocalDate.of(100, 2, 26)},
                {DiscordianDate.of(1266, 1, 58), LocalDate.of(100, 2, 27)},
                {DiscordianDate.of(1266, 1, 59), LocalDate.of(100, 2, 28)},
                {DiscordianDate.of(1266, 1, 60), LocalDate.of(100, 3, 1)},
                {DiscordianDate.of(1266, 1, 61), LocalDate.of(100, 3, 2)},

                // End-of-year and historical calendar-continuity samples.
                {DiscordianDate.of(1166, 5, 73), LocalDate.of(0, 12, 31)},
                {DiscordianDate.of(1166, 5, 72), LocalDate.of(0, 12, 30)},
                {DiscordianDate.of(2748, 4, 68), LocalDate.of(1582, 10, 14)},
                {DiscordianDate.of(2748, 4, 69), LocalDate.of(1582, 10, 15)},
                {DiscordianDate.of(3111, 5, 24), LocalDate.of(1945, 11, 12)},
                {DiscordianDate.of(3178, 3, 40), LocalDate.of(2012, 7, 5)},
                {DiscordianDate.of(3178, 3, 41), LocalDate.of(2012, 7, 6)},
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_from_LocalDate(DiscordianDate expectedDiscordianDate, LocalDate isoDate) {
        assertEquals(expectedDiscordianDate, DiscordianDate.from(isoDate));
    }
}
