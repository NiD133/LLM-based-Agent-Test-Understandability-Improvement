package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_multipliedBy_overflowTooBig {

    // Smallest value that overflows int when doubled: (MAX_VALUE / 2 + 1) * 2 > MAX_VALUE
    private static final int HALF_MAX_PLUS_ONE = Integer.MAX_VALUE / 2 + 1;

    @Test
    public void test_multipliedBy_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Years.of(HALF_MAX_PLUS_ONE).multipliedBy(2));
    }
}
