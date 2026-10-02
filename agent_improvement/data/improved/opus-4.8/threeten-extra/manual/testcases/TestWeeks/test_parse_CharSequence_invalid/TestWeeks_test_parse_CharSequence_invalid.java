package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests that {@link Weeks#parse(CharSequence)} rejects malformed input.
 * <p>
 * The accepted format is the ISO-8601 week period {@code PnW} (with an optional
 * leading sign). Every string below violates that format in some way, so parsing
 * each one must fail with a {@link DateTimeParseException}.
 */
public class TestWeeks_test_parse_CharSequence_invalid {

    @ParameterizedTest
    @ValueSource(strings = {
            "P3Y",   // wrong unit: years, not weeks
            "P3M",   // wrong unit: months, not weeks
            "P3D",   // wrong unit: days, not weeks
            "3",     // bare number, missing the "P" prefix and "W" unit
            "-3",    // signed number, still missing "P" and "W"
            "3Y",    // number with unit but no "P" prefix
            "-3Y",   // signed number with unit but no "P" prefix
            "P3",    // has "P" prefix but missing the "W" unit
            "P-3",   // signed amount but missing the "W" unit
            "PY"     // "P" prefix with no numeric amount
    })
    public void parse_rejectsInvalidText(String invalidText) {
        assertThrows(DateTimeParseException.class, () -> Weeks.parse(invalidText));
    }
}
