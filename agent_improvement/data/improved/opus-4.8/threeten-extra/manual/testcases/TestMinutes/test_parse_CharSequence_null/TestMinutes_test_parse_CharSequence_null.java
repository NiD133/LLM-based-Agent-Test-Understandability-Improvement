package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#parse(CharSequence)} rejects a {@code null} argument
 * by throwing a {@link NullPointerException}.
 */
public class TestMinutes_test_parse_CharSequence_null {

    @Test
    public void parse_nullText_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Minutes.parse((CharSequence) null));
    }
}
