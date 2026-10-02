package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Seconds#parse(CharSequence)} accepts an ISO-8601 period
 * text carrying a leading minus sign and negates the whole amount.
 * <p>
 * Each test case supplies an unsigned period text together with the number of
 * seconds it represents on its own. The test then prefixes the text with "-"
 * and checks that parsing yields the negated amount.
 */
public class TestSeconds_test_parse_CharSequence_valid_initialMinus {

    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR = 60 * 60;
    private static final int SECONDS_PER_DAY = 60 * 60 * 24;

    /**
     * Provides {periodText, secondsRepresentedByText} pairs.
     * The pairs are grouped by the kind of period text being exercised.
     */
    public static Object[][] validPeriodTextsAndSeconds() {
        return new Object[][] {
            // Seconds-only sections, including explicit signs.
            { "PT0S", 0 },
            { "PT1S", 1 },
            { "PT2S", 2 },
            { "PT123456789S", 123456789 },
            { "PT+0S", 0 },
            { "PT+2S", 2 },
            { "PT-0S", 0 },
            { "PT-2S", -2 },

            // Minutes-only sections.
            { "PT0M", 0 },
            { "PT1M", 1 * SECONDS_PER_MINUTE },
            { "PT2M", 2 * SECONDS_PER_MINUTE },
            { "PT1234M", 1234 * SECONDS_PER_MINUTE },
            { "PT+0M", 0 },
            { "PT+2M", 2 * SECONDS_PER_MINUTE },
            { "PT-0M", 0 },
            { "PT-2M", -2 * SECONDS_PER_MINUTE },

            // Hours-only sections.
            { "PT0H", 0 },
            { "PT1H", 1 * SECONDS_PER_HOUR },
            { "PT2H", 2 * SECONDS_PER_HOUR },
            { "PT1234H", 1234 * SECONDS_PER_HOUR },
            { "PT+0H", 0 },
            { "PT+2H", 2 * SECONDS_PER_HOUR },
            { "PT-0H", 0 },
            { "PT-2H", -2 * SECONDS_PER_HOUR },

            // Days-only sections.
            { "P0D", 0 },
            { "P1D", 1 * SECONDS_PER_DAY },
            { "P2D", 2 * SECONDS_PER_DAY },
            { "P1234D", 1234 * SECONDS_PER_DAY },
            { "P+0D", 0 },
            { "P+2D", 2 * SECONDS_PER_DAY },
            { "P-0D", 0 },
            { "P-2D", -2 * SECONDS_PER_DAY },

            // Combined minutes and seconds.
            { "PT0M0S", 0 },
            { "PT2M3S", 2 * SECONDS_PER_MINUTE + 3 },
            { "PT+2M3S", 2 * SECONDS_PER_MINUTE + 3 },
            { "PT2M+3S", 2 * SECONDS_PER_MINUTE + 3 },
            { "PT-2M3S", -2 * SECONDS_PER_MINUTE + 3 },
            { "PT2M-3S", 2 * SECONDS_PER_MINUTE - 3 },
            { "PT-2M-3S", -2 * SECONDS_PER_MINUTE - 3 },

            // Combined hours and seconds.
            { "PT0H0S", 0 },
            { "PT2H3S", 2 * SECONDS_PER_HOUR + 3 },
            { "PT+2H3S", 2 * SECONDS_PER_HOUR + 3 },
            { "PT2H+3S", 2 * SECONDS_PER_HOUR + 3 },
            { "PT-2H3S", -2 * SECONDS_PER_HOUR + 3 },
            { "PT2H-3S", 2 * SECONDS_PER_HOUR - 3 },
            { "PT-2H-3S", -2 * SECONDS_PER_HOUR - 3 },

            // Full days/hours/minutes/seconds form.
            { "P0DT0H0M0S", 0 },
            { "P5DT2H4M3S", 5 * SECONDS_PER_DAY + 2 * SECONDS_PER_HOUR + 4 * SECONDS_PER_MINUTE + 3 },
        };
    }

    @ParameterizedTest
    @MethodSource("validPeriodTextsAndSeconds")
    public void parse_withLeadingMinus_negatesTheWholeAmount(String periodText, int secondsWithoutLeadingMinus) {
        Seconds parsed = Seconds.parse("-" + periodText);

        assertEquals(Seconds.of(-secondsWithoutLeadingMinus), parsed);
    }
}
