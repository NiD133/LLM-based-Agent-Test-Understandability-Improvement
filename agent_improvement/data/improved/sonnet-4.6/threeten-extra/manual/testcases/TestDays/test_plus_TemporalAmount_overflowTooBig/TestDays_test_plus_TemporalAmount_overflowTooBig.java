package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_TemporalAmount_overflowTooBig {

    /**
     * Adding 2 days to (Integer.MAX_VALUE - 1) days must overflow,
     * because the result exceeds Integer.MAX_VALUE.
     */
    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        Days nearMaxDays = Days.of(Integer.MAX_VALUE - 1);
        Days twoDays = Days.of(2);

        assertThrows(ArithmeticException.class, () -> nearMaxDays.plus(twoDays));
    }
}
