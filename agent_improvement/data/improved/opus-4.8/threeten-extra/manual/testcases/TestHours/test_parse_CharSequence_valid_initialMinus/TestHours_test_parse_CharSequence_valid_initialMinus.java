package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link Hours#parse(CharSequence)} when a leading minus sign is prepended
 * to an otherwise valid ISO-8601 period string.
 * <p>
 * A leading "-" negates the whole amount, so parsing {@code "-" + text} must yield the
 * negation of the amount that {@code text} represents on its own.
 */
public class TestHours_test_parse_CharSequence_valid_initialMinus {

    /**
     * Each case pairs a valid period string with the number of hours it represents
     * when parsed without any leading sign. Day-based strings contribute 24 hours per day.
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // hours-only forms ("PTnH")
            { "PT0H", 0 },
            { "PT1H", 1 },
            { "PT2H", 2 },
            { "PT123456789H", 123456789 },
            { "PT+0H", 0 },
            { "PT+2H", 2 },
            { "PT-0H", 0 },
            { "PT-2H", -2 },
            // day-only forms ("PnD"), 24 hours per day
            { "P0D", 0 * 24 },
            { "P1D", 1 * 24 },
            { "P2D", 2 * 24 },
            { "P1234567D", 1234567 * 24 },
            { "P+0D", 0 * 24 },
            { "P+2D", 2 * 24 },
            { "P-0D", 0 * 24 },
            { "P-2D", -2 * 24 },
            // combined day-and-hour forms ("PnDTnH")
            { "P0DT0H", 0 },
            { "P1DT2H", 1 * 24 + 2 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialMinus(String text, int hoursWithoutSign) {
        // Prepending "-" negates the whole period, so the result is the negated amount.
        Hours parsed = Hours.parse("-" + text);

        assertEquals(Hours.of(-hoursWithoutSign), parsed);
    }
}
