package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_int {

    @Test
    public void test_plus_int() {
        Weeks fiveWeeks = Weeks.of(5);

        assertEquals(Weeks.of(5), fiveWeeks.plus(0));
        assertEquals(Weeks.of(7), fiveWeeks.plus(2));
        assertEquals(Weeks.of(3), fiveWeeks.plus(-2));

        Weeks oneLessThanMax = Weeks.of(Integer.MAX_VALUE - 1);
        assertEquals(Weeks.of(Integer.MAX_VALUE), oneLessThanMax.plus(1));

        Weeks oneMoreThanMin = Weeks.of(Integer.MIN_VALUE + 1);
        assertEquals(Weeks.of(Integer.MIN_VALUE), oneMoreThanMin.plus(-1));
    }
}
