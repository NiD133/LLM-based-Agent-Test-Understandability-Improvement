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

@SuppressWarnings("static-method")
public class TestPaxChronology_test_until_TemporalUnit {

    public static Object[][] data_until() {
        return new Object[][] {
                // Days
                {2014, 5, 26, 2014, 5, 26, DAYS, 0},
                {2014, 5, 26, 2014, 6, 4, DAYS, 6},
                {2014, 5, 26, 2014, 5, 20, DAYS, -6},

                // Weeks
                {2014, 5, 26, 2014, 5, 26, WEEKS, 0},
                {2014, 5, 26, 2014, 6, 4, WEEKS, 0},
                {2014, 5, 26, 2014, 6, 5, WEEKS, 1},

                // Months
                {2014, 5, 26, 2014, 5, 26, MONTHS, 0},
                {2014, 5, 26, 2014, 6, 25, MONTHS, 0},
                {2014, 5, 26, 2014, 6, 26, MONTHS, 1},

                // Years and larger year-based units
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

                // Eras
                {-2013, 5, 26, 0, 5, 26, ERAS, 0},
                {-2013, 5, 26, 2014, 5, 26, ERAS, 1},

                // Pax leap-month boundary cases
                {2011, 13, 26, 2013, 13, 26, YEARS, 2},
                {2011, 13, 26, 2012, 14, 26, YEARS, 1},
                {2012, 14, 26, 2011, 13, 26, YEARS, -1},
                {2012, 14, 26, 2013, 13, 26, YEARS, 1},
                {2011, 13, 6, 2012, 13, 6, YEARS, 0},
                {2012, 13, 6, 2011, 13, 6, YEARS, 0},
                {2011, 13, 1, 2012, 13, 7, YEARS, 0},
                {2012, 13, 7, 2011, 13, 1, YEARS, 0},
                {2011, 12, 28, 2012, 13, 1, YEARS, 1},
                {2012, 13, 1, 2011, 12, 28, YEARS, -1},
                {2013, 13, 6, 2012, 13, 6, YEARS, -1},
                {2012, 13, 6, 2013, 13, 6, YEARS, 1},
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int startYear,
            int startMonth,
            int startDayOfMonth,
            int endYear,
            int endMonth,
            int endDayOfMonth,
            TemporalUnit unit,
            long expected) {

        PaxDate start = PaxDate.of(startYear, startMonth, startDayOfMonth);
        PaxDate end = PaxDate.of(endYear, endMonth, endDayOfMonth);

        assertEquals(expected, start.until(end, unit));
    }
}
