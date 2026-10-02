package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_until_end {

    /**
     * Provides test data for verifying the period between two Pax dates.
     * Each row: start(year, month, day), end(year, month, day), expected period(years, months, days).
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
            // Same date -> zero period
            { 2014, 5, 26,   2014, 5, 26,   0,  0,  0 },

            // Days-only differences (within same month or across months)
            { 2014, 5, 26,   2014, 6,  4,   0,  0,  6 },
            { 2014, 5, 26,   2014, 5, 20,   0,  0, -6 },
            { 2014, 5, 26,   2014, 6,  5,   0,  0,  7 },
            { 2014, 5, 26,   2014, 6, 25,   0,  0, 27 },

            // One full month boundary
            { 2014, 5, 26,   2014, 6, 26,   0,  1,  0 },

            // Cross-year spans
            { 2014, 5, 26,   2015, 5, 25,   0, 12, 27 },
            { 2014, 5, 26,   2015, 5, 26,   1,  0,  0 },
            { 2014, 5, 26,   2024, 5, 25,   9, 12, 27 },

            // Leap-year edge cases (months 13 and 14 around leap years)
            { 2011, 13, 26,   2013, 13, 26,   2,  0,  0 },
            { 2011, 13, 26,   2012, 14, 26,   1,  0,  0 },
            { 2012, 14, 26,   2011, 13, 26,  -1,  0,  0 },
            { 2012, 14, 26,   2013, 13, 26,   1,  0,  0 },

            // Spanning across the leap month (month 13 in one year, month 13 in another)
            { 2011, 13,  6,   2012, 13,  6,   0, 13,  0 },
            { 2012, 13,  6,   2011, 13,  6,   0, -13,  0 },
            { 2011, 13,  1,   2012, 13,  7,   0, 13,  6 },
            { 2012, 13,  7,   2011, 13,  1,   0, -13, -6 },

            // Boundary between month 12 and leap month 13
            { 2011, 12, 28,   2012, 13,  1,   1,  0,  1 },
            { 2012, 13,  1,   2011, 12, 28,  -1,  0, -1 },

            // Reverse direction across leap month boundary
            { 2013, 13,  6,   2012, 13,  6,  -1, -1,  0 },
            { 2012, 13,  6,   2013, 13,  6,   1,  0,  0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(int year1, int month1, int dom1,
                               int year2, int month2, int dom2,
                               int yearPeriod, int monthPeriod, int dayPeriod) {
        PaxDate start = PaxDate.of(year1, month1, dom1);
        PaxDate end = PaxDate.of(year2, month2, dom2);
        ChronoPeriod period = PaxChronology.INSTANCE.period(yearPeriod, monthPeriod, dayPeriod);
        assertEquals(period, start.until(end));
    }
}
