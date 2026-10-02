package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Hours fiveHours = Hours.of(5);

        //noinspection DataFlowIssue - intentionally verifies the null contract
        assertThrows(NullPointerException.class, () -> fiveHours.compareTo(null));
    }
}
