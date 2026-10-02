package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#plus(java.time.temporal.TemporalAmount)} when the added
 * amount is a {@link Period} expressed in whole weeks.
 */
public class TestWeeks_test_plus_TemporalAmount_Period {

    @Test
    public void plus_periodOfWeeks_addsWeeks() {
        Weeks fiveWeeks = Weeks.of(5);

        // Adding zero weeks leaves the amount unchanged.
        assertEquals(Weeks.of(5), fiveWeeks.plus(Period.ofWeeks(0)));
        // Adding a positive period increases the amount.
        assertEquals(Weeks.of(7), fiveWeeks.plus(Period.ofWeeks(2)));
        // Adding a negative period decreases the amount.
        assertEquals(Weeks.of(3), fiveWeeks.plus(Period.ofWeeks(-2)));

        // Addition is allowed right up to the int boundaries.
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE - 1).plus(Period.ofWeeks(1)));
        assertEquals(Weeks.of(Integer.MIN_VALUE), Weeks.of(Integer.MIN_VALUE + 1).plus(Period.ofWeeks(-1)));
    }
}
