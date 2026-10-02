package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Minutes fiveMinutes = Minutes.of(5);

        //noinspection DataFlowIssue - testing null handling
        assertThrows(NullPointerException.class, () -> fiveMinutes.compareTo(null));
    }
}
