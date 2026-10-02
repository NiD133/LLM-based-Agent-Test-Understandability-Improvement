package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_compareTo {

    @Test
    public void test_compareTo() {
        Weeks fiveWeeks = Weeks.of(5);
        Weeks sixWeeks = Weeks.of(6);

        assertEquals(0, fiveWeeks.compareTo(fiveWeeks));
        assertEquals(-1, fiveWeeks.compareTo(sixWeeks));
        assertEquals(1, sixWeeks.compareTo(fiveWeeks));
    }
}
