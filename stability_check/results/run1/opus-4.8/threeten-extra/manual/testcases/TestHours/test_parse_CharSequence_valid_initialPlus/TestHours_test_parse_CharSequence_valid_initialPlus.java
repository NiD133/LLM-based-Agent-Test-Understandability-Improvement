package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Hours#parse(CharSequence)} accepts an explicit leading
 * plus sign in front of an otherwise valid ISO-8601 amount.
 * <p>
 * Each case supplies the amount text <em>without</em> the leading sign together
 * with the number of hours it represents. The test prepends "+" to the text and
 * checks that parsing yields the same {@link Hours} value as the unsigned form,
 * i.e. the leading plus does not change the result.
 */
public class TestHours_test_parse_CharSequence_valid_initialPlus {

    private static final int HOURS_PER_DAY = 24;

    /**
     * Provides pairs of (amount text without leading sign, expected number of hours).
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // Hour-only forms: "PTnH"
            { "PT0H", 0 },
            { "PT1H", 1 },
            { "PT2H", 2 },
            { "PT123456789H", 123456789 },
            { "PT+0H", 0 },
            { "PT+2H", 2 },
            { "PT-0H", 0 },
            { "PT-2H", -2 },
            // Day-only forms: "PnD" (each day counts as 24 hours)
            { "P0D", 0 * HOURS_PER_DAY },
            { "P1D", 1 * HOURS_PER_DAY },
            { "P2D", 2 * HOURS_PER_DAY },
            { "P1234567D", 1234567 * HOURS_PER_DAY },
            { "P+0D", 0 * HOURS_PER_DAY },
            { "P+2D", 2 * HOURS_PER_DAY },
            { "P-0D", 0 * HOURS_PER_DAY },
            { "P-2D", -2 * HOURS_PER_DAY },
            // Combined day-and-hour forms: "PnDTnH"
            { "P0DT0H", 0 },
            { "P1DT2H", 1 * HOURS_PER_DAY + 2 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialPlus(String amountText, int expectedHours) {
        Hours parsed = Hours.parse("+" + amountText);

        assertEquals(Hours.of(expectedHours), parsed);
    }
}
