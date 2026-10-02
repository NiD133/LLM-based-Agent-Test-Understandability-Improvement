package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSeconds_test_parse_CharSequence_null {

    @Test
    @DisplayName("parse(null) throws NullPointerException")
    public void test_parse_CharSequence_null() {
        assertThrows(NullPointerException.class, () -> Seconds.parse((CharSequence) null));
    }
}
