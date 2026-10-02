package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestMinutes_test_parse_CharSequence_invalid {

    public static Object[][] data_invalid() {
        return new Object[][] {
            // unsupported ISO-8601 units (weeks, quarters, months)
            { "P3W" },
            { "P3Q" },
            { "P1M2Y" },

            // missing ISO-8601 "P" prefix
            { "3" },
            { "-3" },
            { "3M" },
            { "-3M" },

            // months are not supported
            { "P3M" },

            // incomplete or bare "P" / "T" prefixes with no value
            { "P3" },
            { "P-3" },
            { "PM" },
            { "T3" },

            // duplicate entry (same value tested twice for robustness)
            { "P3M" },

            // seconds are not supported
            { "PT3S" },

            // "T" prefix with no recognised time unit suffix
            { "PT3" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Minutes.parse(str));
    }
}
