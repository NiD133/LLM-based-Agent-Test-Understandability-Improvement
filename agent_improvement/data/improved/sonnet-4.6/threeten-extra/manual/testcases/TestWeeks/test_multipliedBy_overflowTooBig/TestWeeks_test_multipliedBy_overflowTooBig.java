package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        // Smallest value whose double exceeds Integer.MAX_VALUE
        int justAboveHalfMax = Integer.MAX_VALUE / 2 + 1;
        assertThrows(ArithmeticException.class, () -> Weeks.of(justAboveHalfMax).multipliedBy(2));
    }
}
