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

/**
 * Tests that {@code Symmetry010Date.minus(amount, unit)} is the inverse of {@code plus}:
 * if adding {@code amount} units to {@code startDate} yields {@code endDate},
 * then subtracting {@code amount} units from {@code endDate} must yield {@code startDate}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_minus_TemporalUnit {

    /**
     * Each row: { startYear, startMonth, startDay, amount, unit, endYear, endMonth, endDay }
     * Semantics: startDate + amount*unit == endDate,
     * so endDate - amount*unit must equal startDate (tested in test_minus_TemporalUnit).
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            { 2014,  5, 26,  0, DAYS,      2014,  5, 26 },
            { 2014,  5, 26,  8, DAYS,      2014,  6,  3 },
            { 2014,  5, 26, -3, DAYS,      2014,  5, 23 },
            { 2014,  5, 26,  0, WEEKS,     2014,  5, 26 },
            { 2014,  5, 26,  3, WEEKS,     2014,  6, 16 },
            { 2014,  5, 26, -5, WEEKS,     2014,  4, 21 },
            { 2014,  5, 26,  0, MONTHS,    2014,  5, 26 },
            { 2014,  5, 26,  3, MONTHS,    2014,  8, 26 },
            { 2014,  5, 26, -5, MONTHS,    2013, 12, 26 },
            { 2014,  5, 26,  0, YEARS,     2014,  5, 26 },
            { 2014,  5, 26,  3, YEARS,     2017,  5, 26 },
            { 2014,  5, 26, -5, YEARS,     2009,  5, 26 },
            { 2014,  5, 26,  0, DECADES,   2014,  5, 26 },
            { 2014,  5, 26,  3, DECADES,   2044,  5, 26 },
            { 2014,  5, 26, -5, DECADES,   1964,  5, 26 },
            { 2014,  5, 26,  0, CENTURIES, 2014,  5, 26 },
            { 2014,  5, 26,  3, CENTURIES, 2314,  5, 26 },
            { 2014,  5, 26, -5, CENTURIES, 1514,  5, 26 },
            { 2014,  5, 26,  0, MILLENNIA, 2014,  5, 26 },
            { 2014,  5, 26,  3, MILLENNIA, 5014,  5, 26 },
            { 2014,  5, 26, -1, MILLENNIA, 1014,  5, 26 },
            { 2014, 12, 26,  3, WEEKS,     2015,  1, 17 },
            { 2014,  1, 26, -5, WEEKS,     2013, 12, 21 },
            { 2012,  6, 26,  3, WEEKS,     2012,  7, 17 },
            { 2012,  7, 26, -5, WEEKS,     2012,  6, 21 },
            { 2012,  6, 21, 53, WEEKS,     2013,  6, 28 },
            { 2013,  6, 21, 313, WEEKS,    2019,  6, 21 },
        };
    }

    /**
     * Verifies the minus direction: subtracting {@code amount} units from {@code endDate}
     * returns {@code startDate}, mirroring the plus operation in the data set.
     */
    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_minus_TemporalUnit(
            int startYear, int startMonth, int startDay,
            long amount, TemporalUnit unit,
            int endYear, int endMonth, int endDay) {
        Symmetry010Date startDate = Symmetry010Date.of(startYear, startMonth, startDay);
        Symmetry010Date endDate   = Symmetry010Date.of(endYear,   endMonth,   endDay);
        assertEquals(startDate, endDate.minus(amount, unit));
    }
}
