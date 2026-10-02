package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#minus(java.time.temporal.TemporalAmount)} when the amount
 * to subtract is supplied as a {@link Period} expressed in whole weeks.
 */
public class TestWeeks_test_minus_TemporalAmount_Period {

    @Test
    public void minus_periodOfWeeks_subtractsTheWeeks() {
        Weeks fiveWeeks = Weeks.of(5);

        // Subtracting zero weeks leaves the value unchanged.
        assertEquals(Weeks.of(5), fiveWeeks.minus(Period.ofWeeks(0)));

        // Subtracting a positive amount decreases the value: 5 - 2 = 3.
        assertEquals(Weeks.of(3), fiveWeeks.minus(Period.ofWeeks(2)));

        // Subtracting a negative amount increases the value: 5 - (-2) = 7.
        assertEquals(Weeks.of(7), fiveWeeks.minus(Period.ofWeeks(-2)));

        // Subtracting -1 from (MAX_VALUE - 1) reaches exactly MAX_VALUE.
        assertEquals(
                Weeks.of(Integer.MAX_VALUE),
                Weeks.of(Integer.MAX_VALUE - 1).minus(Period.ofWeeks(-1)));

        // Subtracting 1 from (MIN_VALUE + 1) reaches exactly MIN_VALUE.
        assertEquals(
                Weeks.of(Integer.MIN_VALUE),
                Weeks.of(Integer.MIN_VALUE + 1).minus(Period.ofWeeks(1)));
    }
}
