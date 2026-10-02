package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Hours#parse(CharSequence)} accepts an explicit leading
 * "+" sign in front of an otherwise valid ISO-8601 period text.
 * <p>
 * Each case takes a base period string, prepends a "+" sign, and checks that the
 * parsed result equals the expected number of hours. Note that day-based inputs
 * (e.g. "P2D") are converted to hours using 24 hours per day.
 */
public class TestHours_test_parse_CharSequence_valid_initialPlus {

    private static final int HOURS_PER_DAY = 24;

    /**
     * Provides {basePeriodText, expectedHours} pairs.
     * The leading "+" sign is added by the test itself.
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // hours-only inputs
            { "PT0H", 0 },
            { "PT1H", 1 },
            { "PT2H", 2 },
            { "PT123456789H", 123456789 },
            { "PT+0H", 0 },
            { "PT+2H", 2 },
            { "PT-0H", 0 },
            { "PT-2H", -2 },
            // day-only inputs (converted to hours)
            { "P0D", 0 * HOURS_PER_DAY },
            { "P1D", 1 * HOURS_PER_DAY },
            { "P2D", 2 * HOURS_PER_DAY },
            { "P1234567D", 1234567 * HOURS_PER_DAY },
            { "P+0D", 0 * HOURS_PER_DAY },
            { "P+2D", 2 * HOURS_PER_DAY },
            { "P-0D", 0 * HOURS_PER_DAY },
            { "P-2D", -2 * HOURS_PER_DAY },
            // combined day-and-hour inputs
            { "P0DT0H", 0 },
            { "P1DT2H", 1 * HOURS_PER_DAY + 2 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialPlus(String basePeriodText, int expectedHours) {
        String textWithLeadingPlus = "+" + basePeriodText;

        Hours parsed = Hours.parse(textWithLeadingPlus);

        assertEquals(Hours.of(expectedHours), parsed);
    }
}
