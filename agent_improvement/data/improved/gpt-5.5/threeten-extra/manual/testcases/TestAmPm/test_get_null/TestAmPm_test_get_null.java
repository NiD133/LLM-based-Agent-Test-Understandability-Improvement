package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_get_null {

    @Test
    public void test_get_null() {
        //noinspection DataFlowIssue - testing null handling
        assertThrows(NullPointerException.class, () -> AmPm.PM.get(null));
    }
}
