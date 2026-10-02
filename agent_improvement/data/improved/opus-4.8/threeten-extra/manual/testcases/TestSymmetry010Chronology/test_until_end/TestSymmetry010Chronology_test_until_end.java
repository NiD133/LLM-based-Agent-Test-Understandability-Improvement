package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry010Date#until(java.time.chrono.ChronoLocalDate)}, verifying that the
 * period elapsed between two Symmetry010 dates matches the expected
 * year/month/day {@link ChronoPeriod}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_until_end {

    /**
     * Test cases for {@link #test_until_end}.
     * <p>
     * Each row is: start date (year, month, day), end date (year, month, day),
     * followed by the expected period between them (years, months, days).
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
            // start == end, so the period is zero
            { 2014, 5, 26, 2014, 5, 26, 0, 0, 0 },
            // a few days forward / backward within the same month
            { 2014, 5, 26, 2014, 6, 4, 0, 0, 9 },
            { 2014, 5, 26, 2014, 5, 20, 0, 0, -6 },
            { 2014, 5, 26, 2014, 6, 5, 0, 0, 10 },
            { 2014, 5, 26, 2014, 6, 25, 0, 0, 30 },
            // exactly one month later
            { 2014, 5, 26, 2014, 6, 26, 0, 1, 0 },
            // just short of one year, then exactly one year later
            { 2014, 5, 26, 2015, 5, 25, 0, 11, 29 },
            { 2014, 5, 26, 2015, 5, 26, 1, 0, 0 },
            // almost ten years later
            { 2014, 5, 26, 2024, 5, 25, 9, 11, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            int expectedYears, int expectedMonths, int expectedDays) {
        Symmetry010Date start = Symmetry010Date.of(year1, month1, dom1);
        Symmetry010Date end = Symmetry010Date.of(year2, month2, dom2);
        ChronoPeriod expectedPeriod =
                Symmetry010Chronology.INSTANCE.period(expectedYears, expectedMonths, expectedDays);

        assertEquals(expectedPeriod, start.until(end));
    }
}
