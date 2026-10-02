package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_int_overflowTooBig {

    @Test
    public void test_plus_int_overflowTooBig() {
        // MAX_VALUE - 1 + 2 exceeds Integer.MAX_VALUE, so Math.addExact must throw
        assertThrows(ArithmeticException.class, () -> Years.of(Integer.MAX_VALUE - 1).plus(2));
    }
}
