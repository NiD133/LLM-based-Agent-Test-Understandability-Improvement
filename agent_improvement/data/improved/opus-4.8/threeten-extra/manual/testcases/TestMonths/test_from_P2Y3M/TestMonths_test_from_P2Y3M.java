package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#from(java.time.temporal.TemporalAmount)} converts a
 * year-and-month temporal amount into the equivalent total number of months.
 */
public class TestMonths_test_from_P2Y3M {

    @Test
    public void test_from_P2Y3M() {
        // A temporal amount of 2 years and 3 months should convert to (2 * 12) + 3 = 27 months.
        int years = 2;
        int months = 3;
        Months expected = Months.of(27);

        Months actual = Months.from(new MockYearsMonths(years, months));

        assertEquals(expected, actual);
    }
}
