package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSeconds_test_parse_CharSequence_valid_initialPlus {

    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR = 60 * SECONDS_PER_MINUTE;
    private static final int SECONDS_PER_DAY = 24 * SECONDS_PER_HOUR;

    public static Object[][] data_valid() {
        return new Object[][] {
                { "PT0S", 0 },
                { "PT1S", 1 },
                { "PT2S", 2 },
                { "PT123456789S", 123456789 },
                { "PT+0S", 0 },
                { "PT+2S", 2 },
                { "PT-0S", 0 },
                { "PT-2S", -2 },

                { "PT0M", 0 },
                { "PT1M", SECONDS_PER_MINUTE },
                { "PT2M", 2 * SECONDS_PER_MINUTE },
                { "PT1234M", 1234 * SECONDS_PER_MINUTE },
                { "PT+0M", 0 },
                { "PT+2M", 2 * SECONDS_PER_MINUTE },
                { "PT-0M", 0 },
                { "PT-2M", -2 * SECONDS_PER_MINUTE },

                { "PT0H", 0 },
                { "PT1H", SECONDS_PER_HOUR },
                { "PT2H", 2 * SECONDS_PER_HOUR },
                { "PT1234H", 1234 * SECONDS_PER_HOUR },
                { "PT+0H", 0 },
                { "PT+2H", 2 * SECONDS_PER_HOUR },
                { "PT-0H", 0 },
                { "PT-2H", -2 * SECONDS_PER_HOUR },

                { "P0D", 0 },
                { "P1D", SECONDS_PER_DAY },
                { "P2D", 2 * SECONDS_PER_DAY },
                { "P1234D", 1234 * SECONDS_PER_DAY },
                { "P+0D", 0 },
                { "P+2D", 2 * SECONDS_PER_DAY },
                { "P-0D", 0 },
                { "P-2D", -2 * SECONDS_PER_DAY },

                { "PT0M0S", 0 },
                { "PT2M3S", 2 * SECONDS_PER_MINUTE + 3 },
                { "PT+2M3S", 2 * SECONDS_PER_MINUTE + 3 },
                { "PT2M+3S", 2 * SECONDS_PER_MINUTE + 3 },
                { "PT-2M3S", -2 * SECONDS_PER_MINUTE + 3 },
                { "PT2M-3S", 2 * SECONDS_PER_MINUTE - 3 },
                { "PT-2M-3S", -2 * SECONDS_PER_MINUTE - 3 },

                { "PT0H0S", 0 },
                { "PT2H3S", 2 * SECONDS_PER_HOUR + 3 },
                { "PT+2H3S", 2 * SECONDS_PER_HOUR + 3 },
                { "PT2H+3S", 2 * SECONDS_PER_HOUR + 3 },
                { "PT-2H3S", -2 * SECONDS_PER_HOUR + 3 },
                { "PT2H-3S", 2 * SECONDS_PER_HOUR - 3 },
                { "PT-2H-3S", -2 * SECONDS_PER_HOUR - 3 },

                { "P0DT0H0M0S", 0 },
                { "P5DT2H4M3S", 5 * SECONDS_PER_DAY + 2 * SECONDS_PER_HOUR + 4 * SECONDS_PER_MINUTE + 3 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialPlus(String str, int expectedSeconds) {
        assertEquals(Seconds.of(expectedSeconds), Seconds.parse("+" + str));
    }
}
