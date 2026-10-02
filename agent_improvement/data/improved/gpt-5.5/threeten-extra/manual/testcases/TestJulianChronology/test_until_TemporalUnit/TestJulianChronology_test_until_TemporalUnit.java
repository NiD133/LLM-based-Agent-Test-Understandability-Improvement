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

public class TestJulianChronology_test_until_TemporalUnit {

    public static Object[][] data_until() {
        return new Object[][] {
            // Days count every whole day between the two Julian dates.
            {2014, 5, 26, 2014, 5, 26, DAYS, 0},
            {2014, 5, 26, 2014, 6, 1, DAYS, 6},
            {2014, 5, 26, 2014, 5, 20, DAYS, -6},

            // Weeks truncate partial weeks toward zero.
            {2014, 5, 26, 2014, 5, 26, WEEKS, 0},
            {2014, 5, 26, 2014, 6, 1, WEEKS, 0},
            {2014, 5, 26, 2014, 6, 2, WEEKS, 1},

            // Month and larger date-based units require the end day to reach
            // the start day before a full unit is counted.
            {2014, 5, 26, 2014, 5, 26, MONTHS, 0},
            {2014, 5, 26, 2014, 6, 25, MONTHS, 0},
            {2014, 5, 26, 2014, 6, 26, MONTHS, 1},

            {2014, 5, 26, 2014, 5, 26, YEARS, 0},
            {2014, 5, 26, 2015, 5, 25, YEARS, 0},
            {2014, 5, 26, 2015, 5, 26, YEARS, 1},

            {2014, 5, 26, 2014, 5, 26, DECADES, 0},
            {2014, 5, 26, 2024, 5, 25, DECADES, 0},
            {2014, 5, 26, 2024, 5, 26, DECADES, 1},

            {2014, 5, 26, 2014, 5, 26, CENTURIES, 0},
            {2014, 5, 26, 2114, 5, 25, CENTURIES, 0},
            {2014, 5, 26, 2114, 5, 26, CENTURIES, 1},

            {2014, 5, 26, 2014, 5, 26, MILLENNIA, 0},
            {2014, 5, 26, 3014, 5, 25, MILLENNIA, 0},
            {2014, 5, 26, 3014, 5, 26, MILLENNIA, 1},

            // Era calculations cross from BC-style proleptic years to AD.
            {-2013, 5, 26, 0, 5, 26, ERAS, 0},
            {-2013, 5, 26, 2014, 5, 26, ERAS, 1}
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int startYear,
            int startMonth,
            int startDay,
            int endYear,
            int endMonth,
            int endDay,
            TemporalUnit unit,
            long expected) {

        JulianDate start = JulianDate.of(startYear, startMonth, startDay);
        JulianDate end = JulianDate.of(endYear, endMonth, endDay);

        assertEquals(expected, start.until(end, unit));
    }
}
