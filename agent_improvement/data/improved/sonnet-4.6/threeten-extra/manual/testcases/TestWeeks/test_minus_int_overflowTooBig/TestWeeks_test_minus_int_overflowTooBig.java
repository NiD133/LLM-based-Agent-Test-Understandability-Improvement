package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_int_overflowTooBig {

    @Test
    public void test_minus_int_overflowTooBig() {
        // subtracting -2 is equivalent to adding 2, so (MAX_VALUE - 1) + 2 overflows int
        assertThrows(ArithmeticException.class, () -> Weeks.of(Integer.MAX_VALUE - 1).minus(-2));
    }
}
