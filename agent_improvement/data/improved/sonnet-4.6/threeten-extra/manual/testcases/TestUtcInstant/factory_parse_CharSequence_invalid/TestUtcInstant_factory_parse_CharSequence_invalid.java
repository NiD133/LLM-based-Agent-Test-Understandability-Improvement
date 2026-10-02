package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link UtcInstant#parse(CharSequence)} rejects malformed or invalid input
 * by throwing {@link DateTimeException}.
 */
public class TestUtcInstant_factory_parse_CharSequence_invalid {

    /**
     * Provides strings that are syntactically or semantically invalid UTC instants.
     * Each entry represents one case that must be rejected by {@code UtcInstant.parse}.
     */
    public static Object[][] data_badParse() {
        return new Object[][] {
            { "" },                        // empty string — no content to parse
            { "A" },                       // single non-digit character — completely malformed
            { "2012-13-01T00:00:00Z" },    // month 13 — out of valid range
        };
    }

    @ParameterizedTest
    @MethodSource("data_badParse")
    public void factory_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeException.class, () -> UtcInstant.parse(str));
    }
}
