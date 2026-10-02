package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Weeks#parse(CharSequence)} throws {@link NullPointerException}
 * when a null argument is passed, as required by the method contract.
 */
public class TestWeeks_test_parse_CharSequence_null {

    @Test
    public void test_parse_CharSequence_null() {
        // Weeks.parse() requires a non-null CharSequence; passing null must throw NPE
        assertThrows(NullPointerException.class, () -> Weeks.parse((CharSequence) null));
    }
}
