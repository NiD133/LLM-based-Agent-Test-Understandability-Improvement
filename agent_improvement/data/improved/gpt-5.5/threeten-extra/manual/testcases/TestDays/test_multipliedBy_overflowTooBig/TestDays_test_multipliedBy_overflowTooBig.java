package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        assertThrows(
                ArithmeticException.class,
                () -> Days.of(Integer.MAX_VALUE / 2 + 1).multipliedBy(2));
    }
}
