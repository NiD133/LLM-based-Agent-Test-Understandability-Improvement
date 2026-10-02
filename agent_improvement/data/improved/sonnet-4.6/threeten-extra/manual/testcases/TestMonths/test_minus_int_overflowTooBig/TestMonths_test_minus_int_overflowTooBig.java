package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_int_overflowTooBig {

    // Subtracting a negative value is equivalent to addition.
    // Starting near Integer.MAX_VALUE and subtracting -2 pushes the result past MAX_VALUE.
    @Test
    public void test_minus_int_overflowTooBig() {
        int nearMax = Integer.MAX_VALUE - 1;
        assertThrows(ArithmeticException.class, () -> Months.of(nearMax).minus(-2));
    }
}
