package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#plus(int)}, which returns a new {@code Years} whose amount
 * is this amount plus the given number of years.
 */
public class TestYears_test_plus_int {

    @Test
    public void test_plus_int() {
        Years fiveYears = Years.of(5);

        // Adding zero years leaves the amount unchanged.
        assertEquals(Years.of(5), fiveYears.plus(0));

        // Adding a positive amount increases the years.
        assertEquals(Years.of(7), fiveYears.plus(2));

        // Adding a negative amount decreases the years.
        assertEquals(Years.of(3), fiveYears.plus(-2));

        // Adding right up to the int boundaries is allowed (no overflow).
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
