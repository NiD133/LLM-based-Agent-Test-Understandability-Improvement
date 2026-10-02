package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        //noinspection DataFlowIssue - intentionally verifies null handling
        assertThrows(NullPointerException.class, () -> Minutes.of(Integer.MIN_VALUE + 1).minus(null));
    }
}
