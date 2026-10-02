package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        assertThrows(
                ArithmeticException.class,
                () -> Years.of(Integer.MAX_VALUE - 1).minus(Years.of(-2)));
    }
}
