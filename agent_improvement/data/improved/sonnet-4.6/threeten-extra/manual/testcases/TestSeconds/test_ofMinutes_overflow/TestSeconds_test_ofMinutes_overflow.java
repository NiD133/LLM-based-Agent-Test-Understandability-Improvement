package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofMinutes_overflow {

    // The smallest minute value whose conversion to seconds exceeds Integer.MAX_VALUE.
    // Integer.MAX_VALUE / 60 is the largest safe input; adding 60 guarantees overflow.
    private static final int MINUTES_THAT_OVERFLOW = (Integer.MAX_VALUE / 60) + 60;

    @Test
    public void test_ofMinutes_overflow() {
        assertThrows(ArithmeticException.class, () -> Seconds.ofMinutes(MINUTES_THAT_OVERFLOW));
    }
}
