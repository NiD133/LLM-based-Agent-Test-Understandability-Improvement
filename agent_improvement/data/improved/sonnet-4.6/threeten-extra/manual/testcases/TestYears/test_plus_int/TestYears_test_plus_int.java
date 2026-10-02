package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestYears_test_plus_int {

    @Test
    public void test_plus_int() {
        Years fiveYears = Years.of(5);

        // Adding zero returns the same amount unchanged
        assertEquals(Years.of(5), fiveYears.plus(0));

        // Adding a positive value increases the year count
        assertEquals(Years.of(7), fiveYears.plus(2));

        // Adding a negative value decreases the year count
        assertEquals(Years.of(3), fiveYears.plus(-2));

        // Adding 1 to (MAX_VALUE - 1) should reach Integer.MAX_VALUE without overflow
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).plus(1));

        // Adding -1 to (MIN_VALUE + 1) should reach Integer.MIN_VALUE without overflow
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
