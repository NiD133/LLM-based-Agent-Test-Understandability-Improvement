package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_add_amountOnly_null {

    @Test
    public void test_add_amountOnly_null() {
        //noinspection DataFlowIssue - testing null handling
        assertThrows(NullPointerException.class, () -> MutableClock.epochUTC().add(null));
    }
}
