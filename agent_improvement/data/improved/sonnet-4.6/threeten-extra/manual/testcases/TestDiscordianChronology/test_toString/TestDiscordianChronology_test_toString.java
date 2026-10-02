package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_toString {

    public static Object[][] data_toString() {
        return new Object[][] {
            { DiscordianDate.of(1, 1, 1),       "Discordian YOLD 1-1-01" },
            { DiscordianDate.of(2012, 5, 23),   "Discordian YOLD 2012-5-23" },
            { DiscordianDate.of(2014, 0, 0),    "Discordian YOLD 2014 St. Tib's Day" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(DiscordianDate discordian, String expected) {
        assertEquals(expected, discordian.toString());
    }
}
