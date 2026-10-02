package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_getLong_null {

    @Test
    public void test_getLong_null() {
        //noinspection DataFlowIssue - testing null handling
        assertThrows(NullPointerException.class, () -> Quarter.Q2.getLong(null));
    }
}
