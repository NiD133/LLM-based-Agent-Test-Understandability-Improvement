package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_range_null {

    @Test
    public void rangeRejectsNullTemporalField() {
        //noinspection DataFlowIssue - deliberately verifies the null handling contract
        assertThrows(NullPointerException.class, () -> Quarter.Q1.range(null));
    }
}
