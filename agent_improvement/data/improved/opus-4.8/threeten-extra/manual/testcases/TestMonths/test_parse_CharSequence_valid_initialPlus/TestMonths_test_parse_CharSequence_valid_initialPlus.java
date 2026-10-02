package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Months#parse(CharSequence)} accepts an explicit leading
 * plus sign before the ISO-8601 period text (for example {@code "+P2M"}).
 * A leading plus is treated the same as no sign, so the parsed amount equals
 * the amount obtained from the unsigned text.
 */
public class TestMonths_test_parse_CharSequence_valid_initialPlus {

    /**
     * Supplies valid period strings (without the leading plus) paired with the
     * total number of months they represent. Years are counted as 12 months.
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // months-only sections
            { "P0M", 0 },
            { "P1M", 1 },
            { "P2M", 2 },
            { "P123456789M", 123456789 },
            { "P+0M", 0 },
            { "P+2M", 2 },
            { "P-0M", 0 },
            { "P-2M", -2 },
            // years-only sections (1 year == 12 months)
            { "P0Y", 0 },
            { "P1Y", 12 },
            { "P2Y", 24 },
            { "P1234567Y", 1234567 * 12 },
            { "P+0Y", 0 },
            { "P+2Y", 24 },
            { "P-0Y", 0 },
            { "P-2Y", -24 },
            // combined years and months sections
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
    public void test_parse_CharSequence_valid_initialPlus(String periodText, int expectedMonths) {
        Months parsed = Months.parse("+" + periodText);

        assertEquals(Months.of(expectedMonths), parsed);
    }
}
