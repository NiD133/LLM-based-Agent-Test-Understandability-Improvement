package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class TestHours_test_parse_CharSequence_null {

    @Test
    public void test_parse_CharSequence_null() {
        assertThrows(NullPointerException.class, () -> Hours.parse((CharSequence) null));
    }
}
