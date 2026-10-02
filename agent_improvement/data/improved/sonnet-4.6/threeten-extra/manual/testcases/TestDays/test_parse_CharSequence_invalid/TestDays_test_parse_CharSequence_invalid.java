package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Days#parse(CharSequence)} rejects strings that do not
 * conform to the expected ISO-8601-derived format (PnD / PnW / PnWnD).
 */
public class TestDays_test_parse_CharSequence_invalid {

    /**
     * Returns strings that {@code Days.parse} must reject with
     * {@link DateTimeParseException}, grouped by the reason each input is invalid.
     */
    static Stream<String> data_invalid() {
        return Stream.of(
                // Contains unsupported ISO-8601 designators (years, months, quarters)
                "P3Y",
                "P3M",
                "P3Q",

                // Week component must precede day component (W before D), not after
                "P1D2W",

                // Missing the required 'P' prefix
                "3",
                "-3",
                "3D",
                "-3D",

                // 'P' present but no numeric value follows (incomplete strings)
                "P3",
                "P-3",
                "P",

                // 'P' present but only a bare designator with no number
                "PD",
                "PW"
        );
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Days.parse(str));
    }
}
