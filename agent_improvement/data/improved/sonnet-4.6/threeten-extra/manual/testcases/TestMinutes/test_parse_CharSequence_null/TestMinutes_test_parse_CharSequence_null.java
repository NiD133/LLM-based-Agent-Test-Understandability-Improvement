package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Minutes#parse(CharSequence)} throws NullPointerException
 * when given a null argument.
 */
public class TestMinutes_test_parse_CharSequence_null {

    @Test
    public void test_parse_CharSequence_null() {
        // Minutes.parse must reject null with NullPointerException per its contract
        assertThrows(NullPointerException.class, () -> Minutes.parse((CharSequence) null));
    }
}
