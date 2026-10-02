package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#toPeriod()} produces a {@link Period} holding the
 * same number of years as the {@code Years} instance.
 */
public class TestYears_test_toPeriod {

    @Test
    public void toPeriod_returnsPeriodWithMatchingYears() {
        // Check a representative range of negative, zero, and positive amounts.
        for (int yearAmount = -20; yearAmount < 20; yearAmount++) {
            Period expected = Period.ofYears(yearAmount);
            Period actual = Years.of(yearAmount).toPeriod();
            assertEquals(expected, actual);
        }
    }
}
