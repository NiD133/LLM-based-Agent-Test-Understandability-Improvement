package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_parse_CharSequence_null {

    @Test
    public void test_parse_CharSequence_null() {
        //noinspection DataFlowIssue - testing null handling
        assertThrows(NullPointerException.class, () -> Seconds.parse((CharSequence) null));
    }
}
