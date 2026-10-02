package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link Months#parse(CharSequence)} for valid ISO-8601 period strings
 * that are prefixed with an explicit leading minus sign (for example {@code "-P2M"}).
 * <p>
 * A leading minus negates the whole parsed amount, so each canonical string is
 * paired with the number of months it represents on its own, and the test asserts
 * that prefixing it with {@code "-"} yields the negation of that amount.
 */
public class TestMonths_test_parse_CharSequence_valid_initialMinus {

    /**
     * Supplies pairs of (period string, months the string represents without a leading sign).
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            { "P0M", 0 },
            { "P1M", 1 },
            { "P2M", 2 },
            { "P123456789M", 123456789 },
            { "P+0M", 0 },
            { "P+2M", 2 },
            { "P-0M", 0 },
            { "P-2M", -2 },
            { "P0Y", 0 },
            { "P1Y", 12 },
            { "P2Y", 24 },
            { "P1234567Y", 1234567 * 12 },
            { "P+0Y", 0 },
            { "P+2Y", 24 },
            { "P-0Y", 0 },
            { "P-2Y", -24 },
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
    public void test_parse_CharSequence_valid_initialMinus(String periodString, int expectedMonths) {
        // A leading "-" negates the entire amount the period string would otherwise represent.
        Months parsed = Months.parse("-" + periodString);
        assertEquals(Months.of(-expectedMonths), parsed);
    }
}
