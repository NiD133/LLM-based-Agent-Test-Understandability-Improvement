package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#minus(java.time.temporal.TemporalAmount)}.
 * <p>
 * Subtracting one {@code Years} amount from another should return a new
 * {@code Years} whose value is the arithmetic difference of the two amounts.
 */
public class TestYears_test_minus_TemporalAmount_Years {

    @Test
    public void test_minus_TemporalAmount_Years() {
        Years fiveYears = Years.of(5);

        // Subtracting zero leaves the value unchanged.
        assertEquals(Years.of(5), fiveYears.minus(Years.of(0)));

        // Subtracting a positive amount decreases the value: 5 - 2 = 3.
        assertEquals(Years.of(3), fiveYears.minus(Years.of(2)));

        // Subtracting a negative amount increases the value: 5 - (-2) = 7.
        assertEquals(Years.of(7), fiveYears.minus(Years.of(-2)));

        // Subtraction may reach the int boundaries without overflowing.
        assertEquals(
                Years.of(Integer.MAX_VALUE),
                Years.of(Integer.MAX_VALUE - 1).minus(Years.of(-1)));
        assertEquals(
                Years.of(Integer.MIN_VALUE),
                Years.of(Integer.MIN_VALUE + 1).minus(Years.of(1)));
    }
}
