package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        // Integer.MIN_VALUE + 1 avoids overflow when negating internally
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Minutes.of(Integer.MIN_VALUE + 1).minus(null));
    }
}
