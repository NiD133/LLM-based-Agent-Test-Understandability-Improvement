package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_until_end {

    public static Object[][] data_until_period() {
        return new Object[][] {
                { 2014, 5, 26, 2014, 5, 26, 0, 0, 0 },
                { 2014, 5, 26, 2014, 6, 4, 0, 0, 6 },
                { 2014, 5, 26, 2014, 5, 20, 0, 0, -6 },
                { 2014, 5, 26, 2014, 6, 5, 0, 0, 7 },
                { 2014, 5, 26, 2014, 6, 25, 0, 0, 27 },
                { 2014, 5, 26, 2014, 6, 26, 0, 1, 0 },
                { 2014, 5, 26, 2015, 5, 25, 0, 12, 27 },
                { 2014, 5, 26, 2015, 5, 26, 1, 0, 0 },
                { 2014, 5, 26, 2024, 5, 25, 9, 12, 27 },
                { 2011, 13, 26, 2013, 13, 26, 2, 0, 0 },
                { 2011, 13, 26, 2012, 14, 26, 1, 0, 0 },
                { 2012, 14, 26, 2011, 13, 26, -1, 0, 0 },
                { 2012, 14, 26, 2013, 13, 26, 1, 0, 0 },
                { 2011, 13, 6, 2012, 13, 6, 0, 13, 0 },
                { 2012, 13, 6, 2011, 13, 6, 0, -13, 0 },
                { 2011, 13, 1, 2012, 13, 7, 0, 13, 6 },
                { 2012, 13, 7, 2011, 13, 1, 0, -13, -6 },
                { 2011, 12, 28, 2012, 13, 1, 1, 0, 1 },
                { 2012, 13, 1, 2011, 12, 28, -1, 0, -1 },
                { 2013, 13, 6, 2012, 13, 6, -1, -1, 0 },
                { 2012, 13, 6, 2013, 13, 6, 1, 0, 0 },
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

        PaxDate start = PaxDate.of(startYear, startMonth, startDayOfMonth);
        PaxDate end = PaxDate.of(endYear, endMonth, endDayOfMonth);
        ChronoPeriod expectedPeriod = PaxChronology.INSTANCE.period(expectedYears, expectedMonths, expectedDays);

        assertEquals(expectedPeriod, start.until(end));
    }
}
