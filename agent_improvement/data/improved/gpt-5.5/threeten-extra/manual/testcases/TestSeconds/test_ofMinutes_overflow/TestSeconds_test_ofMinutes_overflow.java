package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofMinutes_overflow {

    @Test
    public void test_ofMinutes_overflow() {
        assertThrows(
                ArithmeticException.class,
                () -> Seconds.ofMinutes((Integer.MAX_VALUE / 60) + 60));
    }
}
