package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_int_overflowTooBig {

    @Test
    public void test_minus_int_overflowTooBig() {
        // Subtracting -2 is equivalent to adding 2; combined with MAX_VALUE-1
        // this exceeds Integer.MAX_VALUE and must throw ArithmeticException.
        int nearMaxYears = Integer.MAX_VALUE - 1;
        int negativeAmount = -2; // subtracting a negative value increases the total
        assertThrows(ArithmeticException.class, () -> Years.of(nearMaxYears).minus(negativeAmount));
    }
}
