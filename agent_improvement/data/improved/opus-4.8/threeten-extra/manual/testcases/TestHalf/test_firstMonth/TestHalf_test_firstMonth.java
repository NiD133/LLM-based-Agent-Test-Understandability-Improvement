package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#firstMonth()}, which returns the first of the six months
 * covered by each half-of-year.
 */
public class TestHalf_test_firstMonth {

    @Test
    public void firstMonth_returnsFirstMonthOfEachHalf() {
        // H1 covers January to June, so its first month is January.
        assertEquals(Month.JANUARY, Half.H1.firstMonth());
        // H2 covers July to December, so its first month is July.
        assertEquals(Month.JULY, Half.H2.firstMonth());
    }
}
