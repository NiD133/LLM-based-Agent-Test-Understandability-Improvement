package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#plus(java.time.temporal.TemporalAmount)} when the amount added is a {@link Period}.
 * <p>
 * A {@code Period} expressed purely in years is convertible to {@code Years}, so adding it should
 * behave exactly like adding the equivalent number of years.
 */
public class TestYears_test_plus_TemporalAmount_Period {

    @Test
    public void plus_period_addsYears() {
        Years fiveYears = Years.of(5);

        // Adding zero years leaves the amount unchanged.
        assertEquals(Years.of(5), fiveYears.plus(Period.ofYears(0)));

        // Adding a positive period of years increases the amount.
        assertEquals(Years.of(7), fiveYears.plus(Period.ofYears(2)));

        // Adding a negative period of years decreases the amount.
        assertEquals(Years.of(3), fiveYears.plus(Period.ofYears(-2)));

        // Adding is allowed right up to the int boundaries without overflowing.
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).plus(Period.ofYears(1)));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).plus(Period.ofYears(-1)));
    }
}
