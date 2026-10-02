package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Months#parse(CharSequence)} rejects malformed text.
 *
 * <p>Each input below violates the accepted ISO-8601 period grammar
 * ({@code [-+]?P[-+]?nY[-+]?nM}, with at least one of the year/month
 * sections present), so parsing must fail with a
 * {@link DateTimeParseException}.
 */
public class TestMonths_test_parse_CharSequence_invalid {

    /**
     * Supplies text strings that {@code Months.parse} must reject, paired with
     * the reason each one is invalid.
     */
    public static Object[][] invalidInputs() {
        return new Object[][] {
            { "P3W" },    // weeks suffix is not supported
            { "P3D" },    // days suffix is not supported
            { "P3Q" },    // unknown suffix 'Q'
            { "P1M2Y" },  // sections out of order (months before years)
            { "3" },      // missing leading 'P'
            { "-3" },     // missing leading 'P'
            { "3M" },     // missing leading 'P'
            { "-3M" },    // missing leading 'P'
            { "P3" },     // number without a unit suffix
            { "P-3" },    // signed number without a unit suffix
            { "PM" },     // unit suffix without a number
        };
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    public void parse_rejectsMalformedText(String invalidText) {
        assertThrows(DateTimeParseException.class, () -> Months.parse(invalidText));
    }
}
