package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that adding a {@link JulianChronology} period to a {@link JulianDate}
 * advances the date by the period's years, months and days.
 */
public class TestJulianChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        // Build a Julian period of 0 years, 2 months and 3 days.
        JulianDate startDate = JulianDate.of(2014, 5, 26);
        JulianDate dateAfterPeriod = startDate.plus(JulianChronology.INSTANCE.period(0, 2, 3));

        // 2014-05-26 plus 2 months and 3 days is 2014-07-29.
        assertEquals(JulianDate.of(2014, 7, 29), dateAfterPeriod);
    }
}
