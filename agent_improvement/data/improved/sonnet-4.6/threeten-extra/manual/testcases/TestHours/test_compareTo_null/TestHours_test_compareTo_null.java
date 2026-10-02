package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Hours test5 = Hours.of(5);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> test5.compareTo(null));
    }
}
