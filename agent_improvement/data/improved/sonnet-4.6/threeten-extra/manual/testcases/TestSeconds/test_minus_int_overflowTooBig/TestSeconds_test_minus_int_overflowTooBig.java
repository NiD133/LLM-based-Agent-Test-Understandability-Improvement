package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_int_overflowTooBig {

    @Test
    public void test_minus_int_overflowTooBig() {
        // minus(-2) is equivalent to adding 2; starting near MAX_VALUE causes overflow
        int nearMax = Integer.MAX_VALUE - 1;
        assertThrows(ArithmeticException.class, () -> Seconds.of(nearMax).minus(-2));
    }
}
