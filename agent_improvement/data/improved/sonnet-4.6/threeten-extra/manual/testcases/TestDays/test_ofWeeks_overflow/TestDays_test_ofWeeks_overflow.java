package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_ofWeeks_overflow {

    // The largest week count whose day equivalent (weeks * 7) still fits in an int.
    // Any value beyond this threshold causes Math.multiplyExact to throw ArithmeticException.
    private static final int MAX_WEEKS_WITHOUT_OVERFLOW = Integer.MAX_VALUE / 7;

    @Test
    public void test_ofWeeks_overflow() {
        // Adding 7 to the safe maximum ensures the product overflows an int
        int overflowingWeeks = MAX_WEEKS_WITHOUT_OVERFLOW + 7;
        assertThrows(ArithmeticException.class, () -> Days.ofWeeks(overflowingWeeks));
    }
}
