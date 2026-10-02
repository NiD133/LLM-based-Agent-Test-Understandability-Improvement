package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_minus_leap_and_year_day_TemporalUnit {

    public static Object[][] data_minus_leap_and_year_day() {
        return new Object[][] {
                { 2014, 13, 29, 0, DAYS, 2014, 13, 29 },
                { 2014, 13, 21, 8, DAYS, 2014, 13, 29 },
                { 2015, 1, 3, -3, DAYS, 2014, 13, 29 },
                { 2014, 13, 29, 0, WEEKS, 2014, 13, 29 },
                { 2014, 13, 7, 3, WEEKS, 2014, 13, 29 },
                { 2015, 2, 7, -5, WEEKS, 2014, 13, 29 },
                { 2013, 13, 29, 52, WEEKS, 2014, 13, 29 },
                { 2014, 13, 29, 0, MONTHS, 2014, 13, 29 },
                { 2014, 10, 28, 3, MONTHS, 2014, 13, 29 },
                { 2015, 5, 28, -5, MONTHS, 2014, 13, 29 },
                { 2013, 13, 29, 13, MONTHS, 2014, 13, 29 },
                { 2014, 13, 29, 0, YEARS, 2014, 13, 29 },
                { 2011, 13, 29, 3, YEARS, 2014, 13, 29 },
                { 2019, 13, 29, -5, YEARS, 2014, 13, 29 },
                { 2012, 6, 29, 4 * -6, WEEKS, 2011, 13, 29 },
                { 2012, 6, 29, 4 * 7, WEEKS, 2012, 13, 29 },
                { 2012, 6, 29, 0, DAYS, 2012, 6, 29 },
                { 2012, 6, 21, 8, DAYS, 2012, 6, 29 },
                { 2012, 7, 3, -3, DAYS, 2012, 6, 29 },
                { 2012, 6, 29, 0, WEEKS, 2012, 6, 29 },
                { 2012, 6, 8, 3, WEEKS, 2012, 6, 29 },
                { 2012, 8, 8, -5, WEEKS, 2012, 6, 29 },
                { 2012, 6, 29, 28, WEEKS, 2012, 13, 29 },
                { 2008, 6, 29, 52 * 4, WEEKS, 2012, 6, 29 },
                { 2012, 6, 29, 0, MONTHS, 2012, 6, 29 },
                { 2012, 3, 28, 3, MONTHS, 2012, 6, 29 },
                { 2012, 11, 28, -5, MONTHS, 2012, 6, 29 },
                { 2008, 6, 29, 13 * 4, MONTHS, 2012, 6, 29 },
                { 2012, 6, 29, 0, YEARS, 2012, 6, 29 },
                { 2009, 6, 28, 3, YEARS, 2012, 6, 29 },
                { 2017, 6, 28, -5, YEARS, 2012, 6, 29 },
                { 2008, 6, 29, 4, YEARS, 2012, 6, 29 },
                { 2012, 13, 29, 4 * -7, WEEKS, 2012, 6, 29 },
                { 2011, 13, 29, 4 * 6, WEEKS, 2012, 6, 29 }
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leap_and_year_day")
    public void test_minus_leap_and_year_day_TemporalUnit(
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth,
            long amountToSubtract,
            TemporalUnit unit,
            int startYear,
            int startMonth,
            int startDayOfMonth) {

        InternationalFixedDate startDate = InternationalFixedDate.of(startYear, startMonth, startDayOfMonth);
        InternationalFixedDate expectedDate = InternationalFixedDate.of(expectedYear, expectedMonth, expectedDayOfMonth);

        assertEquals(expectedDate, startDate.minus(amountToSubtract, unit));
    }
}
