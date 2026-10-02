package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_parse_CharSequence_null {

    @Test
    public void test_parse_CharSequence_null() {
        CharSequence nullText = null;

        assertThrows(NullPointerException.class, () -> Weeks.parse(nullText));
    }
}
