package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE / 2 - 1).multipliedBy(2));
    }
}
