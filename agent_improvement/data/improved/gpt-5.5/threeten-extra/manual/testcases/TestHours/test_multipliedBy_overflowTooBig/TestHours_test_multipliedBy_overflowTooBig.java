package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        int tooLargeToDouble = Integer.MAX_VALUE / 2 + 1;

        assertThrows(
                ArithmeticException.class,
                () -> Hours.of(tooLargeToDouble).multipliedBy(2));
    }
}
