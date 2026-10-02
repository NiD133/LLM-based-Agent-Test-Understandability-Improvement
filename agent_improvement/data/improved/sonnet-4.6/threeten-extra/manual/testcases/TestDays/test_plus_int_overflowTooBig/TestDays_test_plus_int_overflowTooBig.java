package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_int_overflowTooBig {

    // Adding 2 to (MAX_VALUE - 1) exceeds Integer.MAX_VALUE, so Math.addExact must throw.
    @Test
    public void test_plus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Days.of(Integer.MAX_VALUE - 1).plus(2));
    }
}
