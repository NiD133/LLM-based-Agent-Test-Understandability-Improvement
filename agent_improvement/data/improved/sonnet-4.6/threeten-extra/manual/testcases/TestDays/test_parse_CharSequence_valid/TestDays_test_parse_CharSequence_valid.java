package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDays_test_parse_CharSequence_valid {

    // Test data: pairs of (ISO-8601 string, expected number of days)
    public static Object[][] data_valid() {
        return new Object[][] {
            // Day-only formats
            { "P0D",         0 },
            { "P1D",         1 },
            { "P2D",         2 },
            { "P123456789D", 123456789 },

            // Day-only with explicit sign
            { "P+0D",        0 },
            { "P+2D",        2 },
            { "P-0D",        0 },
            { "P-2D",       -2 },

            // Week-only formats (each week converts to 7 days)
            { "P0W",         0 },
            { "P1W",         7 },
            { "P2W",        14 },
            { "P12345678W",  12345678 * 7 },

            // Week-only with explicit sign
            { "P+0W",        0 },
            { "P+2W",       14 },
            { "P-0W",        0 },
            { "P-2W",      -14 },

            // Combined week-and-day formats
            { "P0W0D",       0 },
            { "P2W3D",      17 },
            { "P+2W3D",     17 },
            { "P2W+3D",     17 },
            { "P-2W3D",    -11 },
            { "P2W-3D",     11 },
            { "P-2W-3D",   -17 },
        };
    }

    public static Object[][] data_invalid() {
        return new Object[][] {
            { "P3Y" }, { "P3M" }, { "P3Q" }, { "P1D2W" },
            { "3" }, { "-3" }, { "3D" }, { "-3D" },
            { "P3" }, { "P-3" }, { "P" }, { "PD" }, { "PW" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid(String str, int expectedDays) {
        assertEquals(Days.of(expectedDays), Days.parse(str));
    }
}
