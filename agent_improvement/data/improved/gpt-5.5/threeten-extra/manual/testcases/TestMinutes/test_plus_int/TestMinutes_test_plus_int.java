package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_int {

    @Test
    public void test_plus_int() {
        Minutes fiveMinutes = Minutes.of(5);

        assertEquals(Minutes.of(5), fiveMinutes.plus(0));
        assertEquals(Minutes.of(7), fiveMinutes.plus(2));
        assertEquals(Minutes.of(3), fiveMinutes.plus(-2));

        Minutes justBelowMaximum = Minutes.of(Integer.MAX_VALUE - 1);
        assertEquals(Minutes.of(Integer.MAX_VALUE), justBelowMaximum.plus(1));

        Minutes justAboveMinimum = Minutes.of(Integer.MIN_VALUE + 1);
        assertEquals(Minutes.of(Integer.MIN_VALUE), justAboveMinimum.plus(-1));
    }
}
