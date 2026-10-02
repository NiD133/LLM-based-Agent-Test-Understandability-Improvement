package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_int_overflowTooBig {

    @Test
    public void test_minus_int_overflowTooBig() {
        int twoBelowOverflow = Integer.MAX_VALUE - 1;
        int amountToSubtract = -2;

        assertThrows(
                ArithmeticException.class,
                () -> Days.of(twoBelowOverflow).minus(amountToSubtract));
    }
}
