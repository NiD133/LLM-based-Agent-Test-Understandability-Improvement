package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_parse_CharSequence_null {

    @Test
    public void test_parse_CharSequence_null() {
        //noinspection DataFlowIssue - intentionally verifies the null-input contract
        assertThrows(NullPointerException.class, () -> Minutes.parse((CharSequence) null));
    }
}
