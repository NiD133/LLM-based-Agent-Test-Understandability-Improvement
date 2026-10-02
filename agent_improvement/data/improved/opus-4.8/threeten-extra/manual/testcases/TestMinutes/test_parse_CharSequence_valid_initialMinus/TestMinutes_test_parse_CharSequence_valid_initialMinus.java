package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Minutes#parse(CharSequence)} honours a leading minus sign.
 * <p>
 * A leading '-' before the ISO-8601 period negates the whole amount, so parsing
 * {@code "-" + text} must yield the negation of parsing {@code text}.
 */
public class TestMinutes_test_parse_CharSequence_valid_initialMinus {

    /**
     * Each case pairs a valid ISO-8601 period string with the number of minutes
     * it represents on its own (without any leading sign).
     */
    public static Object[][] validTextAndMinutes() {
        return new Object[][] {
            // minutes-only sections
            { "PT0M", 0 },
            { "PT1M", 1 },
            { "PT2M", 2 },
            { "PT123456789M", 123456789 },
            { "PT+0M", 0 },
            { "PT+2M", 2 },
            { "PT-0M", 0 },
            { "PT-2M", -2 },
            // hours-only sections (1 hour = 60 minutes)
            { "PT0H", 0 },
            { "PT1H", 60 },
            { "PT2H", 120 },
            { "PT1234H", 1234 * 60 },
            { "PT+0H", 0 },
            { "PT+2H", 120 },
            { "PT-0H", 0 },
            { "PT-2H", -120 },
            // days-only sections (1 day = 24 * 60 minutes)
            { "P0D", 0 },
            { "P1D", 1 * 24 * 60 },
            { "P2D", 2 * 24 * 60 },
            { "P1234D", 1234 * 24 * 60 },
            { "P+0D", 0 },
            { "P+2D", 2 * 24 * 60 },
            { "P-0D", 0 },
            { "P-2D", -2 * 24 * 60 },
            // combined hours and minutes
            { "PT0H0M", 0 },
            { "PT2H3M", 123 },
            { "PT+2H3M", 123 },
            { "PT2H+3M", 123 },
            { "PT-2H3M", -117 },
            { "PT2H-3M", 117 },
            { "PT-2H-3M", -123 },
            // combined days, hours and minutes
            { "P0DT0H0M", 0 },
            { "P5DT2H4M", 5 * 24 * 60 + 2 * 60 + 4 },
        };
    }

    @ParameterizedTest
    @MethodSource("validTextAndMinutes")
    public void parsingWithLeadingMinusNegatesTheAmount(String text, int minutesWithoutSign) {
        Minutes parsedWithLeadingMinus = Minutes.parse("-" + text);

        assertEquals(Minutes.of(-minutesWithoutSign), parsedWithLeadingMinus);
    }
}
