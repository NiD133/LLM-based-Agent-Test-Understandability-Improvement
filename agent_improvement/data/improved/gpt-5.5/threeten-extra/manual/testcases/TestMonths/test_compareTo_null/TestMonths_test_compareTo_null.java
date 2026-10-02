package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Months fiveMonths = Months.of(5);

        //noinspection DataFlowIssue - intentionally verifies the null contract
        assertThrows(NullPointerException.class, () -> fiveMonths.compareTo(null));
    }
}
