package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#parse(CharSequence)} rejects a {@code null} input.
 */
public class TestYears_test_parse_CharSequence_null {

    @Test
    public void parse_nullText_throwsNullPointerException() {
        CharSequence nullText = null;
        assertThrows(NullPointerException.class, () -> Years.parse(nullText));
    }
}
