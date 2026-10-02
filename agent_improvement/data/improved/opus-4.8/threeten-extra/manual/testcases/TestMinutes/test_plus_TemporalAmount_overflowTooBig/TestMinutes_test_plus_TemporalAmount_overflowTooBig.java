package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_TemporalAmount_overflowTooBig {

    /**
     * Adding 2 minutes to a value just below Integer.MAX_VALUE overflows the
     * int minute count, so plus(...) must throw an ArithmeticException.
     */
    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        Minutes nearMax = Minutes.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> nearMax.plus(Minutes.of(2)));
    }
}
