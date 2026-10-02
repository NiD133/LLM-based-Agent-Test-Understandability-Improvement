package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_plus_TemporalUnit {

    public static Object[][] data_plus() {
        return new Object[][] {
                // Same-day, intra-month, and cross-month movement from a regular date.
                plusCase(2014, 5, 26, 0, DAYS, 2014, 5, 26),
                plusCase(2014, 5, 26, 8, DAYS, 2014, 6, 6),
                plusCase(2014, 5, 26, -3, DAYS, 2014, 5, 23),

                plusCase(2014, 5, 26, 0, WEEKS, 2014, 5, 26),
                plusCase(2014, 5, 26, 3, WEEKS, 2014, 6, 19),
                plusCase(2014, 5, 26, -5, WEEKS, 2014, 4, 19),

                plusCase(2014, 5, 26, 0, MONTHS, 2014, 5, 26),
                plusCase(2014, 5, 26, 3, MONTHS, 2014, 8, 26),
                plusCase(2014, 5, 26, -5, MONTHS, 2013, 13, 26),

                // Larger date-based units keep the month and day when valid.
                plusCase(2014, 5, 26, 0, YEARS, 2014, 5, 26),
                plusCase(2014, 5, 26, 3, YEARS, 2017, 5, 26),
                plusCase(2014, 5, 26, -5, YEARS, 2009, 5, 26),

                plusCase(2014, 5, 26, 0, DECADES, 2014, 5, 26),
                plusCase(2014, 5, 26, 3, DECADES, 2044, 5, 26),
                plusCase(2014, 5, 26, -5, DECADES, 1964, 5, 26),

                plusCase(2014, 5, 26, 0, CENTURIES, 2014, 5, 26),
                plusCase(2014, 5, 26, 3, CENTURIES, 2314, 5, 26),
                plusCase(2014, 5, 26, -5, CENTURIES, 1514, 5, 26),

                plusCase(2014, 5, 26, 0, MILLENNIA, 2014, 5, 26),
                plusCase(2014, 5, 26, 3, MILLENNIA, 5014, 5, 26),
                plusCase(2014, 5, 26, -1, MILLENNIA, 2014 - 1000, 5, 26),

                // Boundary cases around year ends and leap-day placement.
                plusCase(2014, 13, 26, 3, WEEKS, 2015, 1, 19),
                plusCase(2014, 1, 26, -5, WEEKS, 2013, 13, 19),
                plusCase(2012, 6, 26, 3, WEEKS, 2012, 7, 19),
                plusCase(2012, 7, 26, -5, WEEKS, 2012, 6, 19),
                plusCase(2012, 6, 21, 52 + 1, WEEKS, 2013, 6, 28),
                plusCase(2013, 6, 21, 6 * 52 + 1, WEEKS, 2019, 6, 28)
        };
    }

    private static Object[] plusCase(
            int year,
            int month,
            int dayOfMonth,
            long amount,
            TemporalUnit unit,
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth) {

        return new Object[] {
                year,
                month,
                dayOfMonth,
                amount,
                unit,
                expectedYear,
                expectedMonth,
                expectedDayOfMonth
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(
            int year,
            int month,
            int dom,
            long amount,
            TemporalUnit unit,
            int expectedYear,
            int expectedMonth,
            int expectedDom) {

        assertEquals(
                InternationalFixedDate.of(expectedYear, expectedMonth, expectedDom),
                InternationalFixedDate.of(year, month, dom).plus(amount, unit));
    }
}
