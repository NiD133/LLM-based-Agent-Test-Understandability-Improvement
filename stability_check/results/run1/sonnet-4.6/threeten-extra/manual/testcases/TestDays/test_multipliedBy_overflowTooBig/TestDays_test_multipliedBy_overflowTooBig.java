package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        // Any value strictly greater than MAX_VALUE/2, when doubled, exceeds Integer.MAX_VALUE
        int justOverHalfMax = Integer.MAX_VALUE / 2 + 1;
        assertThrows(ArithmeticException.class, () -> Days.of(justOverHalfMax).multipliedBy(2));
    }
}
