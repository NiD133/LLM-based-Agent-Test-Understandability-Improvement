package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.ERAS;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_plus_TemporalUnit {

    private static final int BASE_YEAR = 2014;
    private static final int BASE_MONTH = 5;
    private static final int BASE_DAY = 26;

    public static Object[][] data_plus() {
        return new Object[][] {
                plusCase(0, DAYS, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(8, DAYS, 2014, 6, 3),
                plusCase(-3, DAYS, 2014, 5, 23),

                plusCase(0, WEEKS, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(3, WEEKS, 2014, 6, 16),
                plusCase(-5, WEEKS, 2014, 4, 21),

                plusCase(0, MONTHS, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(3, MONTHS, 2014, 8, 26),
                plusCase(-5, MONTHS, 2013, 12, 26),

                plusCase(0, YEARS, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(3, YEARS, 2017, 5, 26),
                plusCase(-5, YEARS, 2009, 5, 26),

                plusCase(0, DECADES, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(3, DECADES, 2044, 5, 26),
                plusCase(-5, DECADES, 1964, 5, 26),

                plusCase(0, CENTURIES, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(3, CENTURIES, 2314, 5, 26),
                plusCase(-5, CENTURIES, 1514, 5, 26),

                plusCase(0, MILLENNIA, BASE_YEAR, BASE_MONTH, BASE_DAY),
                plusCase(3, MILLENNIA, 5014, 5, 26),
                plusCase(-5, MILLENNIA, 2014 - 5000, 5, 26),

                plusCase(-1, ERAS, -2013, 5, 26)
        };
    }

    private static Object[] plusCase(
            long amount,
            TemporalUnit unit,
            int expectedYear,
            int expectedMonth,
            int expectedDay) {

        return new Object[] {
                BASE_YEAR,
                BASE_MONTH,
                BASE_DAY,
                amount,
                unit,
                expectedYear,
                expectedMonth,
                expectedDay
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
                JulianDate.of(expectedYear, expectedMonth, expectedDom),
                JulianDate.of(year, month, dom).plus(amount, unit));
    }
}
