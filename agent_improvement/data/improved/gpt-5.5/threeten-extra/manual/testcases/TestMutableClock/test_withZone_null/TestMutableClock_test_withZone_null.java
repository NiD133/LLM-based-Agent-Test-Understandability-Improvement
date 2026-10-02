package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_withZone_null {

    @Test
    public void test_withZone_null() {
        assertThrows(
                NullPointerException.class,
                () -> MutableClock.epochUTC().withZone(null));
    }
}
