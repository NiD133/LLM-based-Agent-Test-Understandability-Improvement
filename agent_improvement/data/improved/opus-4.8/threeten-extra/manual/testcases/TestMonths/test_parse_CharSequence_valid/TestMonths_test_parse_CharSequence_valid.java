package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Months#parse(CharSequence)} accepts valid ISO-8601
 * period strings and converts them to the expected number of months.
 * <p>
 * Years are converted to months by multiplying by 12, so {@code "P1Y"}
 * parses to 12 months. A leading sign before {@code P} negates the whole
 * amount, while signs inside each section apply to that section only.
 */
public class TestMonths_test_parse_CharSequence_valid {

    /**
     * Each case is {@code { inputText, expectedMonths }}, grouped by the kind
     * of period string being parsed.
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // Plain month sections.
            { "P0M", 0 },
            { "P1M", 1 },
            { "P2M", 2 },
            { "P123456789M", 123456789 },

            // Month sections with an explicit sign on the number.
            { "P+0M", 0 },
            { "P+2M", 2 },
            { "P-0M", 0 },
            { "P-2M", -2 },

            // Year sections, each year counting as 12 months.
            { "P0Y", 0 },
            { "P1Y", 12 },
            { "P2Y", 24 },
            { "P1234567Y", 1234567 * 12 },

            // Year sections with an explicit sign on the number.
            { "P+0Y", 0 },
            { "P+2Y", 24 },
            { "P-0Y", 0 },
            { "P-2Y", -24 },

            // Combined year and month sections (years * 12 + months).
            { "P0Y0M", 0 },
            { "P2Y3M", 27 },
            { "P+2Y3M", 27 },
            { "P2Y+3M", 27 },
            { "P-2Y3M", -21 },
            { "P2Y-3M", 21 },
            { "P-2Y-3M", -27 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid(String inputText, int expectedMonths) {
        assertEquals(Months.of(expectedMonths), Months.parse(inputText));
    }
}
