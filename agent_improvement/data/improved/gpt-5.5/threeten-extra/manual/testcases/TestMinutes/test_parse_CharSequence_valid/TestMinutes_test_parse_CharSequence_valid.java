package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestMinutes_test_parse_CharSequence_valid {

    private static final int MINUTES_PER_HOUR = 60;
    private static final int MINUTES_PER_DAY = 24 * MINUTES_PER_HOUR;

    public static Object[][] data_valid() {
        return new Object[][] {
                {"PT0M", 0},
                {"PT1M", 1},
                {"PT2M", 2},
                {"PT123456789M", 123456789},
                {"PT+0M", 0},
                {"PT+2M", 2},
                {"PT-0M", 0},
                {"PT-2M", -2},

                {"PT0H", 0},
                {"PT1H", MINUTES_PER_HOUR},
                {"PT2H", 2 * MINUTES_PER_HOUR},
                {"PT1234H", 1234 * MINUTES_PER_HOUR},
                {"PT+0H", 0},
                {"PT+2H", 2 * MINUTES_PER_HOUR},
                {"PT-0H", 0},
                {"PT-2H", -2 * MINUTES_PER_HOUR},

                {"P0D", 0},
                {"P1D", MINUTES_PER_DAY},
                {"P2D", 2 * MINUTES_PER_DAY},
                {"P1234D", 1234 * MINUTES_PER_DAY},
                {"P+0D", 0},
                {"P+2D", 2 * MINUTES_PER_DAY},
                {"P-0D", 0},
                {"P-2D", -2 * MINUTES_PER_DAY},

                {"PT0H0M", 0},
                {"PT2H3M", 123},
                {"PT+2H3M", 123},
                {"PT2H+3M", 123},
                {"PT-2H3M", -117},
                {"PT2H-3M", 117},
                {"PT-2H-3M", -123},

                {"P0DT0H0M", 0},
                {"P5DT2H4M", 5 * MINUTES_PER_DAY + 2 * MINUTES_PER_HOUR + 4}
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid(String text, int expectedMinutes) {
        assertEquals(Minutes.of(expectedMinutes), Minutes.parse(text));
    }
}
