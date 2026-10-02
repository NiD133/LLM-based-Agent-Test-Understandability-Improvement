package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_until_end {

    public static Object[][] data_until_period() {
        return new Object[][] {
                { 2014, 5, 26, 2014, 5, 26, 0, 0, 0 },
                { 2014, 5, 26, 2014, 6, 4, 0, 0, 13 },
                { 2014, 5, 26, 2014, 5, 20, 0, 0, -6 },
                { 2014, 5, 26, 2014, 6, 5, 0, 0, 14 },
                { 2014, 5, 26, 2014, 6, 25, 0, 0, 34 },
                { 2014, 5, 26, 2014, 6, 26, 0, 1, 0 },
                { 2014, 5, 26, 2015, 5, 25, 0, 11, 27 },
                { 2014, 5, 26, 2015, 5, 26, 1, 0, 0 },
                { 2014, 5, 26, 2024, 5, 25, 9, 11, 27 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(
            int startYear,
            int startMonth,
            int startDay,
            int endYear,
            int endMonth,
            int endDay,
            int expectedYears,
            int expectedMonths,
            int expectedDays) {

        Symmetry454Date start = Symmetry454Date.of(startYear, startMonth, startDay);
        Symmetry454Date end = Symmetry454Date.of(endYear, endMonth, endDay);
        ChronoPeriod expectedPeriod = Symmetry454Chronology.INSTANCE.period(expectedYears, expectedMonths, expectedDays);

        assertEquals(expectedPeriod, start.until(end));
    }
}
