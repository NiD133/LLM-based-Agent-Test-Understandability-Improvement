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

    /**
     * Returns test cases for {@link JulianDate#until(java.time.temporal.Temporal, TemporalUnit)}.
     * Each row: year1, month1, day1, year2, month2, day2, unit, expectedAmount
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // DAYS: same date, forward, backward
            { 2014, 5, 26,  2014, 5, 26,  DAYS,      0 },
            { 2014, 5, 26,  2014, 6,  1,  DAYS,      6 },
            { 2014, 5, 26,  2014, 5, 20,  DAYS,     -6 },

            // WEEKS: boundary at exactly 7 days
            { 2014, 5, 26,  2014, 5, 26,  WEEKS,     0 },
            { 2014, 5, 26,  2014, 6,  1,  WEEKS,     0 },
            { 2014, 5, 26,  2014, 6,  2,  WEEKS,     1 },

            // MONTHS: boundary at same day-of-month in next month
            { 2014, 5, 26,  2014, 5, 26,  MONTHS,    0 },
            { 2014, 5, 26,  2014, 6, 25,  MONTHS,    0 },
            { 2014, 5, 26,  2014, 6, 26,  MONTHS,    1 },

            // YEARS: boundary at same month/day in next year
            { 2014, 5, 26,  2014, 5, 26,  YEARS,     0 },
            { 2014, 5, 26,  2015, 5, 25,  YEARS,     0 },
            { 2014, 5, 26,  2015, 5, 26,  YEARS,     1 },

            // DECADES
            { 2014, 5, 26,  2014, 5, 26,  DECADES,   0 },
            { 2014, 5, 26,  2024, 5, 25,  DECADES,   0 },
            { 2014, 5, 26,  2024, 5, 26,  DECADES,   1 },

            // CENTURIES
            { 2014, 5, 26,  2014, 5, 26,  CENTURIES, 0 },
            { 2014, 5, 26,  2114, 5, 25,  CENTURIES, 0 },
            { 2014, 5, 26,  2114, 5, 26,  CENTURIES, 1 },

            // MILLENNIA
            { 2014, 5, 26,  2014, 5, 26,  MILLENNIA, 0 },
            { 2014, 5, 26,  3014, 5, 25,  MILLENNIA, 0 },
            { 2014, 5, 26,  3014, 5, 26,  MILLENNIA, 1 },

            // ERAS: same era vs crossing from BC to AD
            { -2013, 5, 26,    0, 5, 26,  ERAS,      0 },
            { -2013, 5, 26, 2014, 5, 26,  ERAS,      1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            TemporalUnit unit, long expected) {
        JulianDate start = JulianDate.of(year1, month1, dom1);
        JulianDate end   = JulianDate.of(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }
}
