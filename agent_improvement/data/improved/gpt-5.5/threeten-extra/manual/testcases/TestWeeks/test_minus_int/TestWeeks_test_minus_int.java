package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_int {

    @Test
    public void test_minus_int() {
        Weeks fiveWeeks = Weeks.of(5);

        assertEquals(Weeks.of(5), fiveWeeks.minus(0));
        assertEquals(Weeks.of(3), fiveWeeks.minus(2));
        assertEquals(Weeks.of(7), fiveWeeks.minus(-2));

        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Weeks.of(Integer.MIN_VALUE), Weeks.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
