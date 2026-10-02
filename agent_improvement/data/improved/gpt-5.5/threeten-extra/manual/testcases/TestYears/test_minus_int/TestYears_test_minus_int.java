package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_int {

    @Test
    public void test_minus_int() {
        Years fiveYears = Years.of(5);

        assertEquals(Years.of(5), fiveYears.minus(0));
        assertEquals(Years.of(3), fiveYears.minus(2));
        assertEquals(Years.of(7), fiveYears.minus(-2));

        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
