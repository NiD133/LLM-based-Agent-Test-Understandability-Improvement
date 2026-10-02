package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        // Integer.MAX_VALUE / 2 + 1 is the smallest value that overflows when doubled
        int initialDays = Integer.MAX_VALUE / 2 + 1;
        assertThrows(ArithmeticException.class, () -> Days.of(initialDays).multipliedBy(2));
    }
}
