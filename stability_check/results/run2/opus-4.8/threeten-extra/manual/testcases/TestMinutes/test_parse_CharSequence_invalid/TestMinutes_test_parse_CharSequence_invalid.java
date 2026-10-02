package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Minutes#parse(CharSequence)} rejects malformed text.
 */
public class TestMinutes_test_parse_CharSequence_invalid {

    /**
     * Strings that do not conform to the ISO-8601 {@code PnDTnHnM} format
     * accepted by {@link Minutes#parse(CharSequence)}.
     */
    public static Object[][] data_invalid() {
        return new Object[][] {
            { "P3W" },      // weeks are not supported
            { "P3Q" },      // unknown unit 'Q'
            { "P1M2Y" },    // years are not supported and order is wrong
            { "3" },        // missing leading 'P'
            { "-3" },       // missing leading 'P'
            { "3M" },       // missing leading 'P'
            { "-3M" },      // missing leading 'P'
            { "P3M" },      // minutes require a 'T' prefix
            { "P3" },       // missing unit suffix
            { "P-3" },      // missing unit suffix
            { "PM" },       // missing number before 'M'
            { "T3" },       // missing leading 'P' and unit suffix
            { "P3M" },      // minutes require a 'T' prefix (duplicate)
            { "PT3S" },     // seconds are not supported
            { "PT3" },      // missing unit suffix after 'T'
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Minutes.parse(str));
    }
}
