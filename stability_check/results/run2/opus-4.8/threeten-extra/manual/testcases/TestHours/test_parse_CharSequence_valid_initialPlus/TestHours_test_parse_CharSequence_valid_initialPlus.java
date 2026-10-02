package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Hours#parse(CharSequence)} accepts otherwise valid
 * ISO-8601 period strings that carry an explicit leading plus sign.
 * <p>
 * Each case takes a base string known to parse successfully and prefixes it
 * with "+". Because a leading plus is a no-op sign, the parsed result must
 * equal the same number of hours as the un-prefixed string.
 */
public class TestHours_test_parse_CharSequence_valid_initialPlus {

    /**
     * Supplies base period strings together with the number of hours they
     * represent. Day-based inputs are expanded to hours (1 day = 24 hours).
     *
     * @return rows of { baseText, expectedHours }
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // Hour-only forms.
            { "PT0H", 0 },
            { "PT1H", 1 },
            { "PT2H", 2 },
            { "PT123456789H", 123456789 },
            // Hour-only forms with an inner sign on the number.
            { "PT+0H", 0 },
            { "PT+2H", 2 },
            { "PT-0H", 0 },
            { "PT-2H", -2 },
            // Day-only forms (24 hours per day).
            { "P0D", 0 * 24 },
            { "P1D", 1 * 24 },
            { "P2D", 2 * 24 },
            { "P1234567D", 1234567 * 24 },
            // Day-only forms with an inner sign on the number.
            { "P+0D", 0 * 24 },
            { "P+2D", 2 * 24 },
            { "P-0D", 0 * 24 },
            { "P-2D", -2 * 24 },
            // Combined day-and-hour forms.
            { "P0DT0H", 0 },
            { "P1DT2H", 1 * 24 + 2 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialPlus(String baseText, int expectedHours) {
        String textWithLeadingPlus = "+" + baseText;

        Hours parsed = Hours.parse(textWithLeadingPlus);

        assertEquals(Hours.of(expectedHours), parsed);
    }
}
