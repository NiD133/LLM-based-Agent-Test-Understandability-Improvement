package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_until_DAYS {

    public static Object[][] data_samples() {
        return new Object[][] {
                sample(2, 1, 1, -1164, 1, 1),
                sample(166, 1, 1, -1000, 1, 1),
                sample(1156, 1, 1, -10, 1, 1),
                sample(1166, 1, 1, 0, 1, 1),
                sample(1167, 1, 1, 1, 1, 1),
                sample(1167, 1, 2, 1, 1, 2),
                sample(1167, 1, 3, 1, 1, 3),
                sample(1167, 1, 57, 1, 2, 26),
                sample(1167, 1, 58, 1, 2, 27),
                sample(1167, 1, 59, 1, 2, 28),
                sample(1167, 1, 60, 1, 3, 1),
                sample(1170, 1, 57, 4, 2, 26),
                sample(1170, 1, 58, 4, 2, 27),
                sample(1170, 1, 59, 4, 2, 28),
                sample(1170, 0, 0, 4, 2, 29),
                sample(1170, 1, 60, 4, 3, 1),
                sample(1266, 1, 57, 100, 2, 26),
                sample(1266, 1, 58, 100, 2, 27),
                sample(1266, 1, 59, 100, 2, 28),
                sample(1266, 1, 60, 100, 3, 1),
                sample(1266, 1, 61, 100, 3, 2),
                sample(1166, 5, 73, 0, 12, 31),
                sample(1166, 5, 72, 0, 12, 30),
                sample(2748, 4, 68, 1582, 10, 14),
                sample(2748, 4, 69, 1582, 10, 15),
                sample(3111, 5, 24, 1945, 11, 12),
                sample(3178, 3, 40, 2012, 7, 5),
                sample(3178, 3, 41, 2012, 7, 6)
        };
    }

    private static Object[] sample(
            int discordianYear,
            int discordianMonth,
            int discordianDay,
            int isoYear,
            int isoMonth,
            int isoDay) {

        return new Object[] {
                DiscordianDate.of(discordianYear, discordianMonth, discordianDay),
                LocalDate.of(isoYear, isoMonth, isoDay)
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(DiscordianDate discordian, LocalDate iso) {
        assertDaysUntil(discordian, iso.plusDays(0), 0);
        assertDaysUntil(discordian, iso.plusDays(1), 1);
        assertDaysUntil(discordian, iso.plusDays(35), 35);
        assertDaysUntil(discordian, iso.minusDays(40), -40);
    }

    private static void assertDaysUntil(DiscordianDate start, LocalDate end, long expectedDays) {
        assertEquals(expectedDays, start.until(end, DAYS));
    }
}
