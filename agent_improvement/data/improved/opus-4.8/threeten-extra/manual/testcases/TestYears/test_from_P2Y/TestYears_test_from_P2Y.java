package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#from(java.time.temporal.TemporalAmount)} converts a
 * {@link Period} of whole years into the equivalent {@link Years} amount.
 */
public class TestYears_test_from_P2Y {

    @Test
    public void from_periodOfTwoYears_returnsYearsOfTwo() {
        Period twoYearPeriod = Period.ofYears(2);

        Years result = Years.from(twoYearPeriod);

        assertEquals(Years.of(2), result);
    }
}
