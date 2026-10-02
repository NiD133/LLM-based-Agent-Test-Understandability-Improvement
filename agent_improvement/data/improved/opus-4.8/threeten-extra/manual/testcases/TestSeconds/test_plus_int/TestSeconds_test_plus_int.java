package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#plus(int)}, which returns a new {@code Seconds}
 * whose amount is this amount plus the given number of seconds.
 */
public class TestSeconds_test_plus_int {

    @Test
    public void test_plus_int() {
        Seconds fiveSeconds = Seconds.of(5);

        // Adding zero leaves the amount unchanged.
        assertEquals(Seconds.of(5), fiveSeconds.plus(0));

        // Adding a positive amount increases the total.
        assertEquals(Seconds.of(7), fiveSeconds.plus(2));

        // Adding a negative amount decreases the total.
        assertEquals(Seconds.of(3), fiveSeconds.plus(-2));

        // Adding right up to the int boundaries succeeds without overflow.
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
