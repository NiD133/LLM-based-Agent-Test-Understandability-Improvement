package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Years fiveYears = Years.of(5);

        //noinspection DataFlowIssue - testing null handling
        assertThrows(NullPointerException.class, () -> fiveYears.compareTo(null));
    }
}
