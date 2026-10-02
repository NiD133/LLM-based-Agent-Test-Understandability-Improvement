package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Years#parse(CharSequence)} rejects malformed text.
 * <p>
 * A valid string must follow the ISO-8601 {@code PnY} form (optionally signed),
 * for example {@code "P3Y"}. Each input below violates that form and is therefore
 * expected to raise a {@link DateTimeParseException}.
 */
public class TestYears_test_parse_CharSequence_invalid {

    /**
     * Supplies text strings that are not valid {@code Years} representations.
     */
    static Stream<String> invalidTextStrings() {
        return Stream.of(
                "P3M",   // wrong unit: months, not years
                "P3W",   // wrong unit: weeks, not years
                "P3D",   // wrong unit: days, not years
                "3",     // bare number, missing the "P...Y" wrapper
                "-3",    // signed bare number, missing the "P...Y" wrapper
                "3Y",    // missing the leading "P"
                "-3Y",   // missing the leading "P"
                "P3",    // missing the trailing "Y"
                "P-3",   // missing the trailing "Y"
                "PY");   // missing the numeric amount
    }

    @ParameterizedTest
    @MethodSource("invalidTextStrings")
    public void parse_withInvalidText_throwsDateTimeParseException(String invalidText) {
        assertThrows(DateTimeParseException.class, () -> Years.parse(invalidText));
    }
}
