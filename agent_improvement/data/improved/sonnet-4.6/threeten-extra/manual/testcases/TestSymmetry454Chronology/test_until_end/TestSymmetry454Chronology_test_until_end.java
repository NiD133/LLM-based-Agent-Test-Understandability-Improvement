package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_until_end {

    /**
     * Test data for {@link #test_until_end}.
     * Columns: startYear, startMonth, startDay, endYear, endMonth, endDay,
     *          expectedYears, expectedMonths, expectedDays (the period from start to end).
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
            // Same date -> zero period
            { 2014, 5, 26,   2014, 5, 26,   0,  0,  0 },
            // Days-only differences
            { 2014, 5, 26,   2014, 6,  4,   0,  0, 13 },
            { 2014, 5, 26,   2014, 5, 20,   0,  0, -6 },
            { 2014, 5, 26,   2014, 6,  5,   0,  0, 14 },
            { 2014, 5, 26,   2014, 6, 25,   0,  0, 34 },
            // Month boundary
            { 2014, 5, 26,   2014, 6, 26,   0,  1,  0 },
            // Large span: just under one year
            { 2014, 5, 26,   2015, 5, 25,   0, 11, 27 },
            // Exactly one year
            { 2014, 5, 26,   2015, 5, 26,   1,  0,  0 },
            // Nearly a decade
            { 2014, 5, 26,   2024, 5, 25,   9, 11, 27 },
        };
    }

    /**
     * Verifies that {@code start.until(end)} returns the period equal to
     * {@code Symmetry454Chronology.INSTANCE.period(years, months, days)}.
     */
    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            int yearPeriod, int monthPeriod, int dayPeriod) {
        Symmetry454Date start = Symmetry454Date.of(year1, month1, dom1);
        Symmetry454Date end = Symmetry454Date.of(year2, month2, dom2);
        ChronoPeriod expected = Symmetry454Chronology.INSTANCE.period(yearPeriod, monthPeriod, dayPeriod);
        assertEquals(expected, start.until(end));
    }
}
