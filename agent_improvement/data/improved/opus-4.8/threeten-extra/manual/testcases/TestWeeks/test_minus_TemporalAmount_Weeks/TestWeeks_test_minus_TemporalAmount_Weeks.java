package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#minus(java.time.temporal.TemporalAmount)} when the amount
 * to subtract is another {@code Weeks} instance.
 * <p>
 * Subtracting a {@code Weeks} value should return a new {@code Weeks} whose
 * amount equals the difference of the two operands.
 */
public class TestWeeks_test_minus_TemporalAmount_Weeks {

    @Test
    public void subtractingZero_returnsSameAmount() {
        Weeks fiveWeeks = Weeks.of(5);

        assertEquals(Weeks.of(5), fiveWeeks.minus(Weeks.of(0)));
    }

    @Test
    public void subtractingPositiveAmount_decreasesAmount() {
        Weeks fiveWeeks = Weeks.of(5);

        assertEquals(Weeks.of(3), fiveWeeks.minus(Weeks.of(2)));
    }

    @Test
    public void subtractingNegativeAmount_increasesAmount() {
        Weeks fiveWeeks = Weeks.of(5);

        assertEquals(Weeks.of(7), fiveWeeks.minus(Weeks.of(-2)));
    }

    @Test
    public void subtractingNegativeOne_reachesMaxValue() {
        assertEquals(
                Weeks.of(Integer.MAX_VALUE),
                Weeks.of(Integer.MAX_VALUE - 1).minus(Weeks.of(-1)));
    }

    @Test
    public void subtractingPositiveOne_reachesMinValue() {
        assertEquals(
                Weeks.of(Integer.MIN_VALUE),
                Weeks.of(Integer.MIN_VALUE + 1).minus(Weeks.of(1)));
    }
}
