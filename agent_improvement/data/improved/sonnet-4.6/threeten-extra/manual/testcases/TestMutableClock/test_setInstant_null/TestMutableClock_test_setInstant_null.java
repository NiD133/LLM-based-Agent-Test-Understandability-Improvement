package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMutableClock_test_setInstant_null {

    @Test
    @DisplayName("setInstant(null) throws NullPointerException")
    public void test_setInstant_null() {
        MutableClock clock = MutableClock.epochUTC();

        //noinspection DataFlowIssue - intentionally passing null to verify null rejection
        assertThrows(NullPointerException.class, () -> clock.setInstant(null));
    }
}
