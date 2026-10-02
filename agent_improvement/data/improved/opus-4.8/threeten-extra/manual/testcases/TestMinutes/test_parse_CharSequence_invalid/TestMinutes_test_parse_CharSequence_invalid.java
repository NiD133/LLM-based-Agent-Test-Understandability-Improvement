package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Minutes#parse(CharSequence)} rejects malformed text.
 *
 * <p>Every string supplied here violates the ISO-8601 {@code PnDTnHnM} grammar
 * that {@code Minutes.parse} accepts, so each one must raise a
 * {@link DateTimeParseException}.
 */
public class TestMinutes_test_parse_CharSequence_invalid {

    /**
     * Supplies text values that {@code Minutes.parse} must reject.
     * <p>
     * Each value is invalid for a specific reason, noted alongside it.
     */
    public static Object[][] data_invalid() {
        return new Object[][] {
            { "P3W" },    // unsupported unit: weeks
            { "P3Q" },    // unknown unit: Q
            { "P1M2Y" },  // unsupported/out-of-order units
            { "3" },      // missing leading 'P'
            { "-3" },     // missing leading 'P'
            { "3M" },     // missing leading 'P'
            { "-3M" },    // missing leading 'P'
            { "P3M" },    // minutes section without the required 'T' prefix
            { "P3" },     // number with no unit suffix
            { "P-3" },    // signed number with no unit suffix
            { "PM" },     // unit suffix with no number
            { "T3" },     // missing leading 'P' and no unit suffix
            { "P3M" },    // minutes section without the required 'T' prefix (repeated)
            { "PT3S" },   // unsupported unit: seconds
            { "PT3" },    // number with no unit suffix after 'T'
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Minutes.parse(str));
    }
}
