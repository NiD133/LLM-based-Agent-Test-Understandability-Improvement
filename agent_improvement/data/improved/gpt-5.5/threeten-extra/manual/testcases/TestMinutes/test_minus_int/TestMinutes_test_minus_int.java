package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_int {

    @Test
    public void test_minus_int() {
        Minutes test5 = Minutes.of(5);

        assertEquals(Minutes.of(5), test5.minus(0));
        assertEquals(Minutes.of(3), test5.minus(2));
        assertEquals(Minutes.of(7), test5.minus(-2));

        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Minutes.of(Integer.MIN_VALUE), Minutes.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
