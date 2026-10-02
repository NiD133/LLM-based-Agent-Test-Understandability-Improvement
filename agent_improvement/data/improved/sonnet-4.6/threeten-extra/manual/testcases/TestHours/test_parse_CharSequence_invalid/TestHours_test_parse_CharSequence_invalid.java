package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestHours_test_parse_CharSequence_invalid {

    public static Object[][] data_invalid() {
        return new Object[][] {
            // unsupported ISO-8601 units (weeks, quarters, months+years)
            { "P3W" },
            { "P3Q" },
            { "P1M2Y" },

            // missing leading 'P'
            { "3" },
            { "-3" },
            { "3H" },
            { "-3H" },

            // 'H' without the required 'T' prefix, or missing number after 'P'
            { "P3H" },
            { "P3" },
            { "P-3" },
            { "PH" },

            // 'T' present but without a leading 'P', or with no value following
            { "T" },
            { "T3H" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Hours.parse(str));
    }
}
