package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_int_overflowTooBig {

    @Test
    public void test_plus_int_overflowTooBig() {
        // MAX_VALUE - 1 plus 2 exceeds Integer.MAX_VALUE, so addExact must throw
        int nearMaxSeconds = Integer.MAX_VALUE - 1;
        assertThrows(ArithmeticException.class, () -> Seconds.of(nearMaxSeconds).plus(2));
    }
}
