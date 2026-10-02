package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_parse_CharSequence_null {

    /**
     * Verifies that Years.parse throws NullPointerException when given a null CharSequence,
     * as required by the method contract ("text, not null").
     */
    @Test
    public void test_parse_CharSequence_null() {
        assertThrows(NullPointerException.class, () -> Years.parse((CharSequence) null));
    }
}
