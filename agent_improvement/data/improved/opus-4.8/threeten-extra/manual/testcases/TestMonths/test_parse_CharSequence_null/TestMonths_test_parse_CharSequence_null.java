package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#parse(CharSequence)} rejects a null argument.
 */
public class TestMonths_test_parse_CharSequence_null {

    @Test
    public void parse_nullText_throwsNullPointerException() {
        CharSequence nullText = null;
        assertThrows(NullPointerException.class, () -> Months.parse(nullText));
    }
}
