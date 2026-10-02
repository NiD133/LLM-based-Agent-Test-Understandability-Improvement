package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_until_end {

    /**
     * Each case describes the {@link Symmetry454Date#until(java.time.chrono.ChronoLocalDate)}
     * period between two Symmetry454 dates.
     *
     * Columns: startYear, startMonth, startDay, endYear, endMonth, endDay,
     *          expectedYears, expectedMonths, expectedDays
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
            // Same date: zero period.
            { 2014, 5, 26, 2014, 5, 26, 0, 0, 0 },
            // Forward / backward within the same month: only the day differs.
            { 2014, 5, 26, 2014, 6, 4, 0, 0, 13 },
            { 2014, 5, 26, 2014, 5, 20, 0, 0, -6 },
            { 2014, 5, 26, 2014, 6, 5, 0, 0, 14 },
            { 2014, 5, 26, 2014, 6, 25, 0, 0, 34 },
            // Crossing one whole month.
            { 2014, 5, 26, 2014, 6, 26, 0, 1, 0 },
            // Just under a year, then exactly one year.
            { 2014, 5, 26, 2015, 5, 25, 0, 11, 27 },
            { 2014, 5, 26, 2015, 5, 26, 1, 0, 0 },
            // Almost ten years.
            { 2014, 5, 26, 2024, 5, 25, 9, 11, 27 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(int year1, int month1, int dom1,
                               int year2, int month2, int dom2,
                               int yearPeriod, int monthPeriod, int dayPeriod) {
        Symmetry454Date start = Symmetry454Date.of(year1, month1, dom1);
        Symmetry454Date end = Symmetry454Date.of(year2, month2, dom2);
        ChronoPeriod expectedPeriod =
            Symmetry454Chronology.INSTANCE.period(yearPeriod, monthPeriod, dayPeriod);

        assertEquals(expectedPeriod, start.until(end));
    }
}
