package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#from(java.time.temporal.TemporalAmount)} converts a
 * {@link Period} of whole days into the equivalent {@code Days} amount.
 */
public class TestDays_test_from_Period_P2D {

    @Test
    public void from_periodOfTwoDays_returnsDaysOfTwo() {
        Period twoDayPeriod = Period.ofDays(2);
        Days expected = Days.of(2);

        Days actual = Days.from(twoDayPeriod);

        assertEquals(expected, actual);
    }
}
