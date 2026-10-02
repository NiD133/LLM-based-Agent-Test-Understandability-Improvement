package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_int_overflowTooBig {

    @Test
    public void test_minus_int_overflowTooBig() {
        // Subtracting -2 is equivalent to adding 2:
        // (Integer.MAX_VALUE - 1) - (-2) = Integer.MAX_VALUE + 1, which overflows int
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MAX_VALUE - 1).minus(-2));
    }
}
