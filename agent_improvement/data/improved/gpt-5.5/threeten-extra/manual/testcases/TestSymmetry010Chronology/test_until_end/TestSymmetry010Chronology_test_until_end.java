package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestSymmetry010Chronology_test_until_end {

    public static Object[][] data_until_period() {
        return new Object[][] {
                // same date
                { 2014, 5, 26, 2014, 5, 26, 0, 0, 0 },
                // day-only differences within and across adjacent months
                { 2014, 5, 26, 2014, 6, 4, 0, 0, 9 },
                { 2014, 5, 26, 2014, 5, 20, 0, 0, -6 },
                { 2014, 5, 26, 2014, 6, 5, 0, 0, 10 },
                { 2014, 5, 26, 2014, 6, 25, 0, 0, 30 },
                // month and year boundaries
                { 2014, 5, 26, 2014, 6, 26, 0, 1, 0 },
                { 2014, 5, 26, 2015, 5, 25, 0, 11, 29 },
                { 2014, 5, 26, 2015, 5, 26, 1, 0, 0 },
                { 2014, 5, 26, 2024, 5, 25, 9, 11, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(
            int startYear,
            int startMonth,
            int startDayOfMonth,
            int endYear,
            int endMonth,
            int endDayOfMonth,
            int expectedYears,
            int expectedMonths,
            int expectedDays) {

        Symmetry010Date start = Symmetry010Date.of(startYear, startMonth, startDayOfMonth);
        Symmetry010Date end = Symmetry010Date.of(endYear, endMonth, endDayOfMonth);
        ChronoPeriod expectedPeriod = Symmetry010Chronology.INSTANCE.period(expectedYears, expectedMonths, expectedDays);

        assertEquals(expectedPeriod, start.until(end));
    }
}
