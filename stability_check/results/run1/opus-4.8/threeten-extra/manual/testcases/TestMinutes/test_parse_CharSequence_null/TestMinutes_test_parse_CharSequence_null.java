package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#parse(CharSequence)} rejects a {@code null} argument.
 */
public class TestMinutes_test_parse_CharSequence_null {

    @Test
    public void parse_nullText_throwsNullPointerException() {
        CharSequence nullText = null;
        assertThrows(NullPointerException.class, () -> Minutes.parse(nullText));
    }
}
