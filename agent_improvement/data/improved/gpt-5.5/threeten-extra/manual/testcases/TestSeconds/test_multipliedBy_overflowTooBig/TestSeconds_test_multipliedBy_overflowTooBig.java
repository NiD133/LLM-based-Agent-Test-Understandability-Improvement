package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_multipliedBy_overflowTooBig {

    private static final int SECONDS_JUST_OVER_HALF_MAX = Integer.MAX_VALUE / 2 + 1;
    private static final int SCALAR_THAT_OVERFLOWS = 2;

    @Test
    public void test_multipliedBy_overflowTooBig() {
        assertThrows(
                ArithmeticException.class,
                () -> Seconds.of(SECONDS_JUST_OVER_HALF_MAX).multipliedBy(SCALAR_THAT_OVERFLOWS));
    }
}
