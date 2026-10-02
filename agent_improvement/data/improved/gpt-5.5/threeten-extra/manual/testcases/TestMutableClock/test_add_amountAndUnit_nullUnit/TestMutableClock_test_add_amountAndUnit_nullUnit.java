package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_add_amountAndUnit_nullUnit {

    @Test
    public void test_add_amountAndUnit_nullUnit() {
        assertThrows(
                NullPointerException.class,
                () -> MutableClock.epochUTC().add(0, null));
    }
}
