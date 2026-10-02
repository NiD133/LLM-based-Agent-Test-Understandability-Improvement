package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#minus(int)}, which returns a new {@code Years} whose
 * amount is this amount minus the given number of years.
 */
public class TestYears_test_minus_int {

    @Test
    public void test_minus_int() {
        Years fiveYears = Years.of(5);

        // Subtracting zero leaves the amount unchanged.
        assertEquals(Years.of(5), fiveYears.minus(0));

        // Subtracting a positive amount decreases the years.
        assertEquals(Years.of(3), fiveYears.minus(2));

        // Subtracting a negative amount increases the years.
        assertEquals(Years.of(7), fiveYears.minus(-2));

        // Subtraction is allowed right up to the int boundaries.
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
