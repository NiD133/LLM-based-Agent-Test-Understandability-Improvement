package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_abs_overflow {

    @Test
    public void test_abs_overflow() {
        Seconds smallestRepresentableSeconds = Seconds.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> smallestRepresentableSeconds.abs());
    }
}
