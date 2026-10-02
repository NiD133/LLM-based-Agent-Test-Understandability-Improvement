package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_int {

    @Test
    public void test_minus_int() {
        Seconds fiveSeconds = Seconds.of(5);

        // Subtracting zero returns the same value
        assertEquals(Seconds.of(5), fiveSeconds.minus(0));

        // Subtracting a positive number decreases the value
        assertEquals(Seconds.of(3), fiveSeconds.minus(2));

        // Subtracting a negative number increases the value
        assertEquals(Seconds.of(7), fiveSeconds.minus(-2));

        // Subtracting -1 from MAX_VALUE - 1 reaches Integer.MAX_VALUE without overflow
        Seconds nearMaxValue = Seconds.of(Integer.MAX_VALUE - 1);
        assertEquals(Seconds.of(Integer.MAX_VALUE), nearMaxValue.minus(-1));

        // Subtracting 1 from MIN_VALUE + 1 reaches Integer.MIN_VALUE without overflow
        Seconds nearMinValue = Seconds.of(Integer.MIN_VALUE + 1);
        assertEquals(Seconds.of(Integer.MIN_VALUE), nearMinValue.minus(1));
    }
}
