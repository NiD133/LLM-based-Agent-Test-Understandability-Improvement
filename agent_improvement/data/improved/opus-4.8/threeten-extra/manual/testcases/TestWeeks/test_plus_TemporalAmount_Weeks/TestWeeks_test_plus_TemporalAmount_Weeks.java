package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#plus(java.time.temporal.TemporalAmount)} when the amount
 * to add is itself another {@code Weeks} value.
 * <p>
 * Each case starts from {@code Weeks.of(5)} (or a boundary value) and verifies
 * that adding another {@code Weeks} produces the expected total.
 */
public class TestWeeks_test_plus_TemporalAmount_Weeks {

    @Test
    public void plus_anotherWeeksAmount_returnsSummedWeeks() {
        Weeks fiveWeeks = Weeks.of(5);

        // Adding zero weeks leaves the value unchanged: 5 + 0 = 5.
        assertEquals(Weeks.of(5), fiveWeeks.plus(Weeks.of(0)));

        // Adding a positive amount: 5 + 2 = 7.
        assertEquals(Weeks.of(7), fiveWeeks.plus(Weeks.of(2)));

        // Adding a negative amount: 5 + (-2) = 3.
        assertEquals(Weeks.of(3), fiveWeeks.plus(Weeks.of(-2)));

        // Adding right up to the maximum int boundary: (MAX_VALUE - 1) + 1 = MAX_VALUE.
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE - 1).plus(Weeks.of(1)));

        // Adding right down to the minimum int boundary: (MIN_VALUE + 1) + (-1) = MIN_VALUE.
        assertEquals(Weeks.of(Integer.MIN_VALUE), Weeks.of(Integer.MIN_VALUE + 1).plus(Weeks.of(-1)));
    }
}
