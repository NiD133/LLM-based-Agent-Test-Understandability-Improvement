package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofHours_overflow {

    // The maximum number of whole hours whose second-count fits in an int.
    // Any value beyond this will cause Math.multiplyExact(hours, 3600) to overflow.
    private static final int MAX_SAFE_HOURS = Integer.MAX_VALUE / 3600;

    @Test
    public void test_ofHours_overflow() {
        // MAX_SAFE_HOURS + 3600 pushes the product well past Integer.MAX_VALUE
        int overflowingHours = MAX_SAFE_HOURS + 3600;
        assertThrows(ArithmeticException.class, () -> Seconds.ofHours(overflowingHours));
    }
}
