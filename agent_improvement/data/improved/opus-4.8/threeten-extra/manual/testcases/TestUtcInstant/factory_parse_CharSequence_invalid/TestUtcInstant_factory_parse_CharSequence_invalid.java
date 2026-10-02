package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link UtcInstant#parse(CharSequence)} rejects malformed text.
 */
public class TestUtcInstant_factory_parse_CharSequence_invalid {

    /**
     * Text inputs that are not valid ISO-8601 UTC instants and therefore
     * cannot be parsed.
     */
    public static Object[][] data_badParse() {
        return new Object[][] {
            { "" },                       // empty string
            { "A" },                      // not a date-time at all
            { "2012-13-01T00:00:00Z" },   // month 13 is out of range
        };
    }

    @ParameterizedTest
    @MethodSource("data_badParse")
    public void factory_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeException.class, () -> UtcInstant.parse(str));
    }
}
