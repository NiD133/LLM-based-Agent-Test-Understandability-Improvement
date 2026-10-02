package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Days#parse(CharSequence)} rejects malformed text
 * by throwing a {@link DateTimeParseException}.
 */
public class TestDays_test_parse_CharSequence_invalid {

    /**
     * Strings that are not valid ISO-8601 day/week period representations and
     * therefore must not be parsable by {@link Days#parse(CharSequence)}.
     */
    public static Object[][] data_invalid() {
        return new Object[][] {
            // Unsupported period units (only weeks "W" and days "D" are allowed).
            { "P3Y" },   // years not supported
            { "P3M" },   // months not supported
            { "P3Q" },   // quarters not supported
            { "P1D2W" }, // sections out of order: days must follow weeks

            // Missing the mandatory leading "P" designator.
            { "3" },
            { "-3" },
            { "3D" },
            { "-3D" },

            // "P" present but the amount/unit suffix is missing or incomplete.
            { "P3" },  // number without a unit suffix
            { "P-3" }, // signed number without a unit suffix
            { "P" },   // no amount at all
            { "PD" },  // days unit without a number
            { "PW" },  // weeks unit without a number
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Days.parse(str));
    }
}
