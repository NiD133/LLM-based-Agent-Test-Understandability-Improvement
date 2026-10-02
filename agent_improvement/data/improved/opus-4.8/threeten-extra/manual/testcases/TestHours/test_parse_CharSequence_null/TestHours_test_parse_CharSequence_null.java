package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#parse(CharSequence)} rejects a null input
 * by throwing a {@link NullPointerException}.
 */
public class TestHours_test_parse_CharSequence_null {

    @Test
    public void parse_nullText_throwsNullPointerException() {
        CharSequence nullText = null;
        assertThrows(NullPointerException.class, () -> Hours.parse(nullText));
    }
}
