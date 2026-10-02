package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        Minutes almostMaximumMinutes = Minutes.of(Integer.MAX_VALUE - 1);
        Minutes twoMinutes = Minutes.of(2);

        assertThrows(ArithmeticException.class, () -> almostMaximumMinutes.plus(twoMinutes));
    }
}
