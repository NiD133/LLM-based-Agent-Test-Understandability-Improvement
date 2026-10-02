package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_int_overflowTooBig {

    @Test
    public void test_plus_int_overflowTooBig() {
        // Adding 2 to (MAX_VALUE - 1) exceeds Integer.MAX_VALUE, so arithmetic overflow must be detected
        int nearMaxHours = Integer.MAX_VALUE - 1;
        assertThrows(ArithmeticException.class, () -> Hours.of(nearMaxHours).plus(2));
    }
}
