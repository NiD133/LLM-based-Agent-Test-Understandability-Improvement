package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_int_overflowTooBig {

    @Test
    public void test_minus_int_overflowTooBig() {
        int nearMaximumMinutes = Integer.MAX_VALUE - 1;
        int minutesToSubtract = -2;

        assertThrows(ArithmeticException.class, () -> Minutes.of(nearMaximumMinutes).minus(minutesToSubtract));
    }
}
