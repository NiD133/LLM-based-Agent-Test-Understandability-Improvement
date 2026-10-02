package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_multipliedBy_overflowTooBig {

    // The smallest value that, when doubled, exceeds Integer.MAX_VALUE
    private static final int OVERFLOW_BOUNDARY = Integer.MAX_VALUE / 2 + 1;

    @Test
    public void test_multipliedBy_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(OVERFLOW_BOUNDARY).multipliedBy(2));
    }
}
