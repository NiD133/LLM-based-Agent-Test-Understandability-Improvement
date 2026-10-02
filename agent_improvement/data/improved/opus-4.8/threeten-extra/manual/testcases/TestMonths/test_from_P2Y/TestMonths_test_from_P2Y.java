package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#from(java.time.temporal.TemporalAmount)} converts a
 * year-based temporal amount into the equivalent number of months.
 */
public class TestMonths_test_from_P2Y {

    /**
     * A period of 2 years (and 0 months) should be converted to 24 months,
     * because one year equals twelve months.
     */
    @Test
    public void from_twoYears_returnsTwentyFourMonths() {
        int years = 2;
        int months = 0;
        TemporalAmount twoYears = new MockYearsMonths(years, months);

        Months result = Months.from(twoYears);

        Months expectedTwentyFourMonths = Months.of(24);
        assertEquals(expectedTwentyFourMonths, result);
    }
}
