package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_until_end {

    /**
     * Each row: start(year, month, day), end(year, month, day), expected period(years, months, days).
     * Tests that {@code start.until(end)} returns the correct {@link ChronoPeriod}.
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
            // Same date → zero period
            { 2014, 5, 26,  2014, 5, 26,  0,  0,   0 },
            // End is 9 days later (within the same quarter)
            { 2014, 5, 26,  2014, 6,  4,  0,  0,   9 },
            // End is 6 days before start → negative days
            { 2014, 5, 26,  2014, 5, 20,  0,  0,  -6 },
            // End is 10 days later (crosses month boundary)
            { 2014, 5, 26,  2014, 6,  5,  0,  0,  10 },
            // End is 30 days later (one full short month)
            { 2014, 5, 26,  2014, 6, 25,  0,  0,  30 },
            // End is exactly 1 month later (same day-of-month)
            { 2014, 5, 26,  2014, 6, 26,  0,  1,   0 },
            // End is 11 months and 29 days later (just short of one year)
            { 2014, 5, 26,  2015, 5, 25,  0, 11,  29 },
            // End is exactly 1 year later (same month and day)
            { 2014, 5, 26,  2015, 5, 26,  1,  0,   0 },
            // End is approximately 10 years later (9 years, 11 months, 29 days)
            { 2014, 5, 26,  2024, 5, 25,  9, 11,  29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            int yearPeriod, int monthPeriod, int dayPeriod) {
        Symmetry010Date start = Symmetry010Date.of(year1, month1, dom1);
        Symmetry010Date end = Symmetry010Date.of(year2, month2, dom2);
        ChronoPeriod expectedPeriod = Symmetry010Chronology.INSTANCE.period(yearPeriod, monthPeriod, dayPeriod);
        assertEquals(expectedPeriod, start.until(end));
    }
}
