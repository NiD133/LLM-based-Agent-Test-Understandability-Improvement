package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        // Adding 2 to (MAX_VALUE - 1) exceeds int range, so ArithmeticException is expected
        Minutes nearMax = Minutes.of(Integer.MAX_VALUE - 1);
        Minutes two = Minutes.of(2);
        assertThrows(ArithmeticException.class, () -> nearMax.plus(two));
    }
}
