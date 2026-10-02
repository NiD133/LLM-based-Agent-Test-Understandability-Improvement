package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_DiscordianDate_until_LocalDate {

    public static Object[][] data_samples() {
        return new Object[][] {
                sample(DiscordianDate.of(2, 1, 1), LocalDate.of(-1164, 1, 1)),
                sample(DiscordianDate.of(166, 1, 1), LocalDate.of(-1000, 1, 1)),
                sample(DiscordianDate.of(1156, 1, 1), LocalDate.of(-10, 1, 1)),
                sample(DiscordianDate.of(1166, 1, 1), LocalDate.of(0, 1, 1)),
                sample(DiscordianDate.of(1167, 1, 1), LocalDate.of(1, 1, 1)),
                sample(DiscordianDate.of(1167, 1, 2), LocalDate.of(1, 1, 2)),
                sample(DiscordianDate.of(1167, 1, 3), LocalDate.of(1, 1, 3)),
                sample(DiscordianDate.of(1167, 1, 57), LocalDate.of(1, 2, 26)),
                sample(DiscordianDate.of(1167, 1, 58), LocalDate.of(1, 2, 27)),
                sample(DiscordianDate.of(1167, 1, 59), LocalDate.of(1, 2, 28)),
                sample(DiscordianDate.of(1167, 1, 60), LocalDate.of(1, 3, 1)),
                sample(DiscordianDate.of(1170, 1, 57), LocalDate.of(4, 2, 26)),
                sample(DiscordianDate.of(1170, 1, 58), LocalDate.of(4, 2, 27)),
                sample(DiscordianDate.of(1170, 1, 59), LocalDate.of(4, 2, 28)),
                sample(DiscordianDate.of(1170, 0, 0), LocalDate.of(4, 2, 29)),
                sample(DiscordianDate.of(1170, 1, 60), LocalDate.of(4, 3, 1)),
                sample(DiscordianDate.of(1266, 1, 57), LocalDate.of(100, 2, 26)),
                sample(DiscordianDate.of(1266, 1, 58), LocalDate.of(100, 2, 27)),
                sample(DiscordianDate.of(1266, 1, 59), LocalDate.of(100, 2, 28)),
                sample(DiscordianDate.of(1266, 1, 60), LocalDate.of(100, 3, 1)),
                sample(DiscordianDate.of(1266, 1, 61), LocalDate.of(100, 3, 2)),
                sample(DiscordianDate.of(1166, 5, 73), LocalDate.of(0, 12, 31)),
                sample(DiscordianDate.of(1166, 5, 72), LocalDate.of(0, 12, 30)),
                sample(DiscordianDate.of(2748, 4, 68), LocalDate.of(1582, 10, 14)),
                sample(DiscordianDate.of(2748, 4, 69), LocalDate.of(1582, 10, 15)),
                sample(DiscordianDate.of(3111, 5, 24), LocalDate.of(1945, 11, 12)),
                sample(DiscordianDate.of(3178, 3, 40), LocalDate.of(2012, 7, 5)),
                sample(DiscordianDate.of(3178, 3, 41), LocalDate.of(2012, 7, 6)),
        };
    }

    private static Object[] sample(DiscordianDate discordian, LocalDate iso) {
        return new Object[] { discordian, iso };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_until_LocalDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(DiscordianChronology.INSTANCE.period(0, 0, 0), discordian.until(iso));
    }
}
