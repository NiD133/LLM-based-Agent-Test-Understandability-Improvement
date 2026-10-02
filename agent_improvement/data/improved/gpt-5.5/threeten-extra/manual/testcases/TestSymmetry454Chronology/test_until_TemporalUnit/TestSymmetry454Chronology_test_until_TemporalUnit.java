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

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_until_TemporalUnit {

    public static Object[][] data_until() {
        return new Object[][] {
                untilCase(2014, 5, 26, 2014, 5, 26, DAYS, 0),
                untilCase(2014, 5, 26, 2014, 6, 4, DAYS, 13),
                untilCase(2014, 5, 26, 2014, 5, 20, DAYS, -6),

                untilCase(2014, 5, 26, 2014, 5, 26, WEEKS, 0),
                untilCase(2014, 5, 26, 2014, 6, 4, WEEKS, 0),
                untilCase(2014, 5, 26, 2014, 6, 5, WEEKS, 1),

                untilCase(2014, 5, 26, 2014, 5, 26, MONTHS, 0),
                untilCase(2014, 5, 26, 2014, 6, 25, MONTHS, 0),
                untilCase(2014, 5, 26, 2014, 6, 26, MONTHS, 1),

                untilCase(2014, 5, 26, 2014, 5, 26, YEARS, 0),
                untilCase(2014, 5, 26, 2015, 5, 25, YEARS, 0),
                untilCase(2014, 5, 26, 2015, 5, 26, YEARS, 1),

                untilCase(2014, 5, 26, 2014, 5, 26, DECADES, 0),
                untilCase(2014, 5, 26, 2024, 5, 25, DECADES, 0),
                untilCase(2014, 5, 26, 2024, 5, 26, DECADES, 1),

                untilCase(2014, 5, 26, 2014, 5, 26, CENTURIES, 0),
                untilCase(2014, 5, 26, 2114, 5, 25, CENTURIES, 0),
                untilCase(2014, 5, 26, 2114, 5, 26, CENTURIES, 1),

                untilCase(2014, 5, 26, 2014, 5, 26, MILLENNIA, 0),
                untilCase(2014, 5, 26, 3014, 5, 25, MILLENNIA, 0),
                untilCase(2014, 5, 26, 3014, 5, 26, MILLENNIA, 1),

                untilCase(2014, 5, 26, 3014, 5, 26, ERAS, 0),
        };
    }

    private static Object[] untilCase(
            int startYear,
            int startMonth,
            int startDay,
            int endYear,
            int endMonth,
            int endDay,
            TemporalUnit unit,
            long expected) {

        return new Object[] { startYear, startMonth, startDay, endYear, endMonth, endDay, unit, expected };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int year1,
            int month1,
            int dom1,
            int year2,
            int month2,
            int dom2,
            TemporalUnit unit,
            long expected) {

        Symmetry454Date start = Symmetry454Date.of(year1, month1, dom1);
        Symmetry454Date end = Symmetry454Date.of(year2, month2, dom2);

        assertEquals(expected, start.until(end, unit));
    }
}
