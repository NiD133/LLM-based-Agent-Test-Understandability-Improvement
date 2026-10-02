package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_TemporalAmount_Seconds {

    @Test
    public void test_plus_TemporalAmount_Seconds() {
        Seconds fiveSeconds = Seconds.of(5);

        // Adding zero seconds returns the same value
        assertEquals(Seconds.of(5), fiveSeconds.plus(Seconds.of(0)));

        // Adding a positive amount increases the total
        assertEquals(Seconds.of(7), fiveSeconds.plus(Seconds.of(2)));

        // Adding a negative amount decreases the total
        assertEquals(Seconds.of(3), fiveSeconds.plus(Seconds.of(-2)));

        // Boundary: one below MAX_VALUE plus one reaches MAX_VALUE without overflow
        assertEquals(Seconds.of(Integer.MAX_VALUE),
                Seconds.of(Integer.MAX_VALUE - 1).plus(Seconds.of(1)));

        // Boundary: one above MIN_VALUE plus negative one reaches MIN_VALUE without overflow
        assertEquals(Seconds.of(Integer.MIN_VALUE),
                Seconds.of(Integer.MIN_VALUE + 1).plus(Seconds.of(-1)));
    }
}
