package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMutableClock_test_add_amountOnly_null {

    @Test
    @DisplayName("add(null) throws NullPointerException")
    public void test_add_amountOnly_null() {
        MutableClock clock = MutableClock.epochUTC();
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> clock.add(null));
    }
}
