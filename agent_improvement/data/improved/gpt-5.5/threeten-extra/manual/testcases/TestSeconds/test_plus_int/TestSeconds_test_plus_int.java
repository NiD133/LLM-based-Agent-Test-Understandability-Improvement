package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_int {

    @Test
    public void test_plus_int() {
        Seconds fiveSeconds = Seconds.of(5);

        assertEquals(Seconds.of(5), fiveSeconds.plus(0));
        assertEquals(Seconds.of(7), fiveSeconds.plus(2));
        assertEquals(Seconds.of(3), fiveSeconds.plus(-2));
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
