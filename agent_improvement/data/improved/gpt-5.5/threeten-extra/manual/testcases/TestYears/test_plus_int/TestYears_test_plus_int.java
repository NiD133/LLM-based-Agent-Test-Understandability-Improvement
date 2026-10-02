package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_int {

    @Test
    public void test_plus_int() {
        Years fiveYears = Years.of(5);

        assertEquals(Years.of(5), fiveYears.plus(0));
        assertEquals(Years.of(7), fiveYears.plus(2));
        assertEquals(Years.of(3), fiveYears.plus(-2));
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
