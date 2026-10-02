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
            // Same date always yields 0 for any unit
            { 2014, 5, 26, 2014, 5, 26, DAYS,      0 },
            { 2014, 5, 26, 2014, 5, 26, WEEKS,     0 },
            { 2014, 5, 26, 2014, 5, 26, MONTHS,    0 },
            { 2014, 5, 26, 2014, 5, 26, YEARS,     0 },
            { 2014, 5, 26, 2014, 5, 26, DECADES,   0 },
            { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 },
            { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 },

            // DAYS: counts raw calendar days; negative when end is before start
            { 2014, 5, 26, 2014, 6,  4, DAYS,  13 },
            { 2014, 5, 26, 2014, 5, 20, DAYS,  -6 },

            // WEEKS: truncates to whole weeks (7 days each)
            { 2014, 5, 26, 2014, 6,  4, WEEKS, 0 }, // 13 days < 2 whole weeks
            { 2014, 5, 26, 2014, 6,  5, WEEKS, 1 }, // exactly 1 week boundary

            // MONTHS: truncates to whole months (same day-of-month required to complete a month)
            { 2014, 5, 26, 2014, 6, 25, MONTHS, 0 }, // one day short of a full month
            { 2014, 5, 26, 2014, 6, 26, MONTHS, 1 }, // exactly one month later

            // YEARS: truncates to whole years
            { 2014, 5, 26, 2015, 5, 25, YEARS, 0 }, // one day short of a full year
            { 2014, 5, 26, 2015, 5, 26, YEARS, 1 }, // exactly one year later

            // DECADES: truncates to whole decades (10 years)
            { 2014, 5, 26, 2024, 5, 25, DECADES, 0 }, // one day short of a decade
            { 2014, 5, 26, 2024, 5, 26, DECADES, 1 }, // exactly one decade later

            // CENTURIES: truncates to whole centuries (100 years)
            { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 }, // one day short of a century
            { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 }, // exactly one century later

            // MILLENNIA: truncates to whole millennia (1000 years)
            { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 }, // one day short of a millennium
            { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 }, // exactly one millennium later

            // ERAS: both dates are in the same era (CE), so always 0
            { 2014, 5, 26, 3014, 5, 26, ERAS, 0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(int year1, int month1, int dom1, int year2, int month2, int dom2, TemporalUnit unit, long expected) {
        Symmetry454Date start = Symmetry454Date.of(year1, month1, dom1);
        Symmetry454Date end = Symmetry454Date.of(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }
}
