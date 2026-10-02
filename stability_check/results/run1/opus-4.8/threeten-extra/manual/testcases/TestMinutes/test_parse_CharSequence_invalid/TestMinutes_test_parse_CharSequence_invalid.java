package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Minutes#parse(CharSequence)} rejects malformed text.
 * <p>
 * Every string below violates the accepted ISO-8601 based grammar
 * {@code [+-]PnDTnHnM} (for example: an unsupported unit such as weeks, a
 * missing "P" prefix, or an empty/incomplete section), so each one is expected
 * to fail with a {@link DateTimeParseException}.
 */
public class TestMinutes_test_parse_CharSequence_invalid {

    /**
     * Malformed inputs that {@code Minutes.parse} must reject.
     */
    public static Object[][] data_invalid() {
        return new Object[][] {
            { "P3W" },    // "W" (weeks) is not a supported unit
            { "P3Q" },    // "Q" is not a supported unit
            { "P1M2Y" },  // "Y" (years) is not a supported unit
            { "3" },      // missing the mandatory "P" prefix
            { "-3" },     // missing the mandatory "P" prefix
            { "3M" },     // missing the mandatory "P" prefix
            { "-3M" },    // missing the mandatory "P" prefix
            { "P3M" },    // minutes section given without the required "T" prefix
            { "P3" },     // number without a unit suffix
            { "P-3" },    // number without a unit suffix
            { "PM" },     // unit "M" without a number
            { "T3" },     // "T" section without the "P" prefix
            { "P3M" },    // minutes section given without the required "T" prefix
            { "PT3S" },   // "S" (seconds) is not a supported unit
            { "PT3" },    // number without a unit suffix
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Minutes.parse(str));
    }
}
