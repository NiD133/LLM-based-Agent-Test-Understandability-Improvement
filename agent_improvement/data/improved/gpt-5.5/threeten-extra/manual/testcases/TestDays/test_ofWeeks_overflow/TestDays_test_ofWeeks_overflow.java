package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_ofWeeks_overflow {

    @Test
    public void test_ofWeeks_overflow() {
        assertThrows(
                ArithmeticException.class,
                () -> Days.ofWeeks((Integer.MAX_VALUE / 7) + 7));
    }
}
