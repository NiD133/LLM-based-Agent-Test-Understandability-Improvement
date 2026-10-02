package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_multipliedBy_overflowTooBig {

    // The smallest value that, when multiplied by 2, exceeds Integer.MAX_VALUE
    private static final int JUST_ABOVE_HALF_MAX = Integer.MAX_VALUE / 2 + 1;

    @Test
    public void test_multipliedBy_overflowTooBig() {
        assertThrows(ArithmeticException.class,
                () -> Minutes.of(JUST_ABOVE_HALF_MAX).multipliedBy(2));
    }
}
