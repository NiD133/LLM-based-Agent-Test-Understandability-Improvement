package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestHours_test_parse_CharSequence_valid_initialPlus {

    public static Object[][] data_valid() {
        return new Object[][] {
            // Hours-only strings (PTnH)
            { "PT0H",          0 },
            { "PT1H",          1 },
            { "PT2H",          2 },
            { "PT123456789H",  123456789 },
            // Hours-only with explicit sign (PT+nH / PT-nH)
            { "PT+0H",         0 },
            { "PT+2H",         2 },
            { "PT-0H",         0 },
            { "PT-2H",        -2 },
            // Days-only strings (PnD) — each day equals 24 hours
            { "P0D",           0 * 24 },
            { "P1D",           1 * 24 },
            { "P2D",           2 * 24 },
            { "P1234567D",     1234567 * 24 },
            // Days-only with explicit sign (P+nD / P-nD)
            { "P+0D",          0 * 24 },
            { "P+2D",          2 * 24 },
            { "P-0D",          0 * 24 },
            { "P-2D",         -2 * 24 },
            // Combined days and hours (PnDTnH)
            { "P0DT0H",        0 },
            { "P1DT2H",        1 * 24 + 2 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialPlus(String str, int expectedHours) {
        assertEquals(Hours.of(expectedHours), Hours.parse("+" + str));
    }
}
