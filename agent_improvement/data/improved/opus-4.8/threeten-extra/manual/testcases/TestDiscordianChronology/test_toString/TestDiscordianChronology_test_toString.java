package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies the human-readable {@link DiscordianDate#toString()} representation.
 * <p>
 * A Discordian date renders as {@code "Discordian YOLD <year>-<month>-<day>"},
 * except for St. Tib's Day (month 0, day 0), which has its own special wording.
 */
public class TestDiscordianChronology_test_toString {

    /**
     * Each case pairs a Discordian date with the exact string it should produce:
     * <ul>
     * <li>the earliest representable date (year 1)</li>
     * <li>a typical mid-range date</li>
     * <li>St. Tib's Day, the leap-day with its own label</li>
     * </ul>
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            { DiscordianDate.of(1, 1, 1),       "Discordian YOLD 1-1-01" },
            { DiscordianDate.of(2012, 5, 23),   "Discordian YOLD 2012-5-23" },
            { DiscordianDate.of(2014, 0, 0),    "Discordian YOLD 2014 St. Tib's Day" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(DiscordianDate date, String expectedText) {
        assertEquals(expectedText, date.toString());
    }
}
