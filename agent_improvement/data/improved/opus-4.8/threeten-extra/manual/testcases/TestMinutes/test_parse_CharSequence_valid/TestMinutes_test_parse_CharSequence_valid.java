package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Minutes#parse(CharSequence)} accepts valid ISO-8601
 * period strings of the form {@code PnDTnHnM} and converts them to the correct
 * total number of minutes.
 */
public class TestMinutes_test_parse_CharSequence_valid {

    private static final int MINUTES_PER_HOUR = 60;
    private static final int MINUTES_PER_DAY = 24 * 60;

    /**
     * Each case pairs an input string with the total number of minutes it
     * should parse to. The cases are grouped by which period section(s) they
     * exercise: minutes-only, hours-only, days-only, and combinations.
     */
    public static Object[][] data_valid() {
        return new Object[][] {
                // minutes-only section ("...M"), including explicit signs
                {"PT0M", 0},
                {"PT1M", 1},
                {"PT2M", 2},
                {"PT123456789M", 123456789},
                {"PT+0M", 0},
                {"PT+2M", 2},
                {"PT-0M", 0},
                {"PT-2M", -2},

                // hours-only section ("...H"), converted at 60 minutes per hour
                {"PT0H", 0},
                {"PT1H", 1 * MINUTES_PER_HOUR},
                {"PT2H", 2 * MINUTES_PER_HOUR},
                {"PT1234H", 1234 * MINUTES_PER_HOUR},
                {"PT+0H", 0},
                {"PT+2H", 2 * MINUTES_PER_HOUR},
                {"PT-0H", 0},
                {"PT-2H", -2 * MINUTES_PER_HOUR},

                // days-only section ("...D"), converted at 1440 minutes per day
                {"P0D", 0},
                {"P1D", 1 * MINUTES_PER_DAY},
                {"P2D", 2 * MINUTES_PER_DAY},
                {"P1234D", 1234 * MINUTES_PER_DAY},
                {"P+0D", 0},
                {"P+2D", 2 * MINUTES_PER_DAY},
                {"P-0D", 0},
                {"P-2D", -2 * MINUTES_PER_DAY},

                // combined hours + minutes, exercising sign combinations
                {"PT0H0M", 0},
                {"PT2H3M", 2 * MINUTES_PER_HOUR + 3},
                {"PT+2H3M", 2 * MINUTES_PER_HOUR + 3},
                {"PT2H+3M", 2 * MINUTES_PER_HOUR + 3},
                {"PT-2H3M", -2 * MINUTES_PER_HOUR + 3},
                {"PT2H-3M", 2 * MINUTES_PER_HOUR - 3},
                {"PT-2H-3M", -2 * MINUTES_PER_HOUR - 3},

                // combined days + hours + minutes
                {"P0DT0H0M", 0},
                {"P5DT2H4M", 5 * MINUTES_PER_DAY + 2 * MINUTES_PER_HOUR + 4},
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid(String text, int expectedMinutes) {
        assertEquals(Minutes.of(expectedMinutes), Minutes.parse(text));
    }
}
