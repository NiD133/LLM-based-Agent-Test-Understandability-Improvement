package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        int valueThatOverflowsWhenDoubled = Integer.MAX_VALUE / 2 + 1;

        assertThrows(
                ArithmeticException.class,
                () -> Minutes.of(valueThatOverflowsWhenDoubled).multipliedBy(2));
    }
}
