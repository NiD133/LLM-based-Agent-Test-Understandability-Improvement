package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_set_adjuster_null {

    @Test
    public void test_set_adjuster_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> MutableClock.epochUTC().set(null));
    }
}
