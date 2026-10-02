package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_int {

    @Test
    public void test_minus_int() {
        Seconds fiveSeconds = Seconds.of(5);

        assertMinusResult(Seconds.of(5), fiveSeconds, 0);
        assertMinusResult(Seconds.of(3), fiveSeconds, 2);
        assertMinusResult(Seconds.of(7), fiveSeconds, -2);
        assertMinusResult(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1), -1);
        assertMinusResult(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1), 1);
    }

    private void assertMinusResult(Seconds expected, Seconds base, int secondsToSubtract) {
        assertEquals(expected, base.minus(secondsToSubtract));
    }
}
