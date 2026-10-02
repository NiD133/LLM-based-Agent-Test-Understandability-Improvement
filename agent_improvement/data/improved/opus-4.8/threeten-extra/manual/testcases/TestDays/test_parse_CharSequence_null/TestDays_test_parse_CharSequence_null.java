package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Days#parse(CharSequence)} rejects a {@code null} argument.
 */
public class TestDays_test_parse_CharSequence_null {

    @Test
    public void parse_nullText_throwsNullPointerException() {
        // Days.parse must reject null input rather than returning a value.
        assertThrows(
                NullPointerException.class,
                () -> Days.parse((CharSequence) null));
    }
}
