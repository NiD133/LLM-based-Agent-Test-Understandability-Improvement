package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Weeks fiveWeeks = Weeks.of(5);

        assertEquals(Weeks.of(0), fiveWeeks.multipliedBy(0));
        assertEquals(Weeks.of(5), fiveWeeks.multipliedBy(1));
        assertEquals(Weeks.of(10), fiveWeeks.multipliedBy(2));
        assertEquals(Weeks.of(15), fiveWeeks.multipliedBy(3));
        assertEquals(Weeks.of(-15), fiveWeeks.multipliedBy(-3));
    }
}
