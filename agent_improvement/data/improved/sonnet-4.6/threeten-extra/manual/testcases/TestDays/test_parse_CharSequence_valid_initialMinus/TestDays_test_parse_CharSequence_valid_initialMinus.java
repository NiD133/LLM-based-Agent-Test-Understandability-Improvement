package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDays_test_parse_CharSequence_valid_initialMinus {

    public static Object[][] data_valid() {
        return new Object[][] {
            { "P0D",          0 },
            { "P1D",          1 },
            { "P2D",          2 },
            { "P123456789D",  123456789 },
            { "P+0D",         0 },
            { "P+2D",         2 },
            { "P-0D",         0 },
            { "P-2D",         -2 },
            { "P0W",          0 },
            { "P1W",          7 },
            { "P2W",          14 },
            { "P12345678W",   12345678 * 7 },
            { "P+0W",         0 },
            { "P+2W",         14 },
            { "P-0W",         0 },
            { "P-2W",         -14 },
            { "P0W0D",        0 },
            { "P2W3D",        17 },
            { "P+2W3D",       17 },
            { "P2W+3D",       17 },
            { "P-2W3D",       -11 },
            { "P2W-3D",       11 },
            { "P-2W-3D",      -17 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialMinus(String str, int expectedDays) {
        assertEquals(Days.of(-expectedDays), Days.parse("-" + str));
    }
}
