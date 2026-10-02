package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Seconds#parse(CharSequence)} accepts a leading ASCII plus
 * sign in front of an otherwise valid ISO-8601 period string.
 * <p>
 * A leading "+" denotes an explicitly positive amount, so prefixing any valid
 * input with "+" must yield exactly the same result as parsing the input on its
 * own (which equals {@code Seconds.of(expectedSeconds)}).
 */
public class TestSeconds_test_parse_CharSequence_valid_initialPlus {

    // Seconds-per-unit constants, mirroring those used by Seconds itself,
    // to make the expected values below self-explanatory.
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR = 60 * 60;
    private static final int SECONDS_PER_DAY = 60 * 60 * 24;

    /**
     * Supplies valid period strings paired with the number of seconds they
     * represent. Each case is exercised with a leading "+" added by the test.
     *
     * @return rows of {periodText, expectedSeconds}
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // Seconds only
            { "PT0S", 0 },
            { "PT1S", 1 },
            { "PT2S", 2 },
            { "PT123456789S", 123456789 },
            { "PT+0S", 0 },
            { "PT+2S", 2 },
            { "PT-0S", 0 },
            { "PT-2S", -2 },

            // Minutes only
            { "PT0M", 0 },
            { "PT1M", SECONDS_PER_MINUTE },
            { "PT2M", 2 * SECONDS_PER_MINUTE },
            { "PT1234M", 1234 * SECONDS_PER_MINUTE },
            { "PT+0M", 0 },
            { "PT+2M", 2 * SECONDS_PER_MINUTE },
            { "PT-0M", 0 },
            { "PT-2M", -2 * SECONDS_PER_MINUTE },

            // Hours only
            { "PT0H", 0 },
            { "PT1H", SECONDS_PER_HOUR },
            { "PT2H", 2 * SECONDS_PER_HOUR },
            { "PT1234H", 1234 * SECONDS_PER_HOUR },
            { "PT+0H", 0 },
            { "PT+2H", 2 * SECONDS_PER_HOUR },
            { "PT-0H", 0 },
            { "PT-2H", -2 * SECONDS_PER_HOUR },

            // Days only
            { "P0D", 0 },
            { "P1D", SECONDS_PER_DAY },
            { "P2D", 2 * SECONDS_PER_DAY },
            { "P1234D", 1234 * SECONDS_PER_DAY },
            { "P+0D", 0 },
            { "P+2D", 2 * SECONDS_PER_DAY },
            { "P-0D", 0 },
            { "P-2D", -2 * SECONDS_PER_DAY },

            // Minutes combined with seconds (signs on each component)
            { "PT0M0S", 0 },
            { "PT2M3S", 2 * SECONDS_PER_MINUTE + 3 },
            { "PT+2M3S", 2 * SECONDS_PER_MINUTE + 3 },
            { "PT2M+3S", 2 * SECONDS_PER_MINUTE + 3 },
            { "PT-2M3S", -2 * SECONDS_PER_MINUTE + 3 },
            { "PT2M-3S", 2 * SECONDS_PER_MINUTE - 3 },
            { "PT-2M-3S", -2 * SECONDS_PER_MINUTE - 3 },

            // Hours combined with seconds (signs on each component)
            { "PT0H0S", 0 },
            { "PT2H3S", 2 * SECONDS_PER_HOUR + 3 },
            { "PT+2H3S", 2 * SECONDS_PER_HOUR + 3 },
            { "PT2H+3S", 2 * SECONDS_PER_HOUR + 3 },
            { "PT-2H3S", -2 * SECONDS_PER_HOUR + 3 },
            { "PT2H-3S", 2 * SECONDS_PER_HOUR - 3 },
            { "PT-2H-3S", -2 * SECONDS_PER_HOUR - 3 },

            // All units together
            { "P0DT0H0M0S", 0 },
            { "P5DT2H4M3S", 5 * SECONDS_PER_DAY + 2 * SECONDS_PER_HOUR + 4 * SECONDS_PER_MINUTE + 3 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialPlus(String periodText, int expectedSeconds) {
        Seconds parsed = Seconds.parse("+" + periodText);

        assertEquals(Seconds.of(expectedSeconds), parsed);
    }
}
