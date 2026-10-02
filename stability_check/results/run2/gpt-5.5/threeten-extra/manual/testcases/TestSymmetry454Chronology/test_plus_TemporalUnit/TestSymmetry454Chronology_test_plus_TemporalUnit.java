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
public class TestSymmetry454Chronology_test_plus_TemporalUnit {

    private static final int BASE_YEAR = 2014;
    private static final int BASE_MONTH = 5;
    private static final int BASE_DAY = 26;

    public static Object[][] data_plus() {
        return new Object[][] {
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 0, DAYS, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 8, DAYS, 2014, 5, 34),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, -3, DAYS, 2014, 5, 23),

                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 0, WEEKS, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 3, WEEKS, 2014, 6, 12),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, -5, WEEKS, 2014, 4, 19),

                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 0, MONTHS, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 3, MONTHS, 2014, 8, 26),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, -5, MONTHS, 2013, 12, 26),

                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 0, YEARS, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 3, YEARS, 2017, 5, 26),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, -5, YEARS, 2009, 5, 26),

                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 0, DECADES, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 3, DECADES, 2044, 5, 26),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, -5, DECADES, 1964, 5, 26),

                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 0, CENTURIES, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 3, CENTURIES, 2314, 5, 26),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, -5, CENTURIES, 1514, 5, 26),

                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 0, MILLENNIA, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, 3, MILLENNIA, 5014, 5, 26),
                plusCase(BASE_YEAR, BASE_MONTH, BASE_DAY, -1, MILLENNIA, 2014 - 1000, 5, 26),

                plusCase(2014, 12, 26, 3, WEEKS, 2015, 1, 19),
                plusCase(2014, 1, 26, -5, WEEKS, 2013, 12, 19),
                plusCase(2012, 6, 26, 3, WEEKS, 2012, 7, 19),
                plusCase(2012, 7, 26, -5, WEEKS, 2012, 6, 19),
                plusCase(2012, 6, 21, 52 + 1, WEEKS, 2013, 6, 28),
                plusCase(2013, 6, 21, 6 * 52 + 1, WEEKS, 2019, 6, 21),
        };
    }

    private static Object[] plusCase(
            int year,
            int month,
            int dayOfMonth,
            long amountToAdd,
            TemporalUnit unit,
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth) {

        return new Object[] {
                year,
                month,
                dayOfMonth,
                amountToAdd,
                unit,
                expectedYear,
                expectedMonth,
                expectedDayOfMonth,
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
                Symmetry454Date.of(expectedYear, expectedMonth, expectedDom),
                Symmetry454Date.of(year, month, dom).plus(amount, unit));
    }
}
