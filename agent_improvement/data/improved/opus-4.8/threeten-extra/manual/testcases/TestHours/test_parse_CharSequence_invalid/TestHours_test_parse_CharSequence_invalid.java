package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Hours#parse(CharSequence)} rejects malformed text.
 */
public class TestHours_test_parse_CharSequence_invalid {

    /**
     * Supplies text strings that do not match the accepted {@code PnDTnH}
     * format and therefore cannot be parsed into an {@code Hours} value.
     */
    public static Object[][] malformedHourStrings() {
        return new Object[][] {
            { "P3W" },    // weeks are not a supported unit
            { "P3Q" },    // 'Q' is not a recognised suffix
            { "P1M2Y" },  // months and years are not supported
            { "3" },      // missing the leading 'P'
            { "-3" },     // missing the leading 'P'
            { "3H" },     // missing the leading 'P' (and 'T')
            { "-3H" },    // missing the leading 'P' (and 'T')
            { "P3H" },    // hours must be prefixed by 'T'
            { "P3" },     // a number needs a unit suffix
            { "P-3" },    // a number needs a unit suffix
            { "PH" },     // 'H' suffix has no number and no 'T'
            { "T" },      // 'T' alone has no content
            { "T3H" },    // missing the leading 'P'
        };
    }

    @ParameterizedTest
    @MethodSource("malformedHourStrings")
    public void parse_rejectsMalformedText(String text) {
        assertThrows(DateTimeParseException.class, () -> Hours.parse(text));
    }
}
