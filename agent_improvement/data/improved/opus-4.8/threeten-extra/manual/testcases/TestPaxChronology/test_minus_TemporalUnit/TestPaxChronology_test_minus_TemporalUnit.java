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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_minus_TemporalUnit {

    /**
     * Cases for subtracting an amount from a PaxDate.
     * <p>
     * Each row is {@code {startYear, startMonth, startDom, amount, unit, endYear, endMonth, endDom}}
     * and asserts that subtracting {@code amount} of {@code unit} from the end date yields the
     * start date, i.e. {@code end.minus(amount, unit) == start}. These are the "plus" cases run in
     * reverse, since minus is the inverse of plus.
     */
    public static Object[][] data_minus() {
        return new Object[][] {
            { 2014, 5, 26, 0, DAYS, 2014, 5, 26 },
            { 2014, 5, 26, 8, DAYS, 2014, 6, 6 },
            { 2014, 5, 26, -3, DAYS, 2014, 5, 23 },
            { 2014, 5, 26, 0, WEEKS, 2014, 5, 26 },
            { 2014, 5, 26, 3, WEEKS, 2014, 6, 19 },
            { 2014, 5, 26, -5, WEEKS, 2014, 4, 19 },
            { 2014, 5, 26, 0, MONTHS, 2014, 5, 26 },
            { 2014, 5, 26, 3, MONTHS, 2014, 8, 26 },
            { 2014, 5, 26, -5, MONTHS, 2013, 13, 26 },
            { 2014, 5, 26, 0, YEARS, 2014, 5, 26 },
            { 2014, 5, 26, 3, YEARS, 2017, 5, 26 },
            { 2014, 5, 26, -5, YEARS, 2009, 5, 26 },
            { 2014, 5, 26, 0, DECADES, 2014, 5, 26 },
            { 2014, 5, 26, 3, DECADES, 2044, 5, 26 },
            { 2014, 5, 26, -5, DECADES, 1964, 5, 26 },
            { 2014, 5, 26, 0, CENTURIES, 2014, 5, 26 },
            { 2014, 5, 26, 3, CENTURIES, 2314, 5, 26 },
            { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 },
            { 2014, 5, 26, 0, MILLENNIA, 2014, 5, 26 },
            { 2014, 5, 26, 3, MILLENNIA, 5014, 5, 26 },
            { 2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26 },
            { 2014, 5, 26, -1, ERAS, -2013, 5, 26 },
            { 2012, 13, 6, 3, MONTHS, 2013, 2, 6 },
            { 2011, 13, 26, 1, YEARS, 2012, 14, 26 },
            { 2014, 13, 26, -2, YEARS, 2012, 14, 26 },
            { 2012, 14, 26, -6, YEARS, 2006, 14, 26 },
            { 2012, 13, 6, -6, YEARS, 2006, 13, 6 },
            { -2014, 5, 26, 0, MONTHS, -2014, 5, 26 },
            { -2014, 5, 26, 3, MONTHS, -2014, 8, 26 },
            { -2014, 5, 26, -5, MONTHS, -2015, 13, 26 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_TemporalUnit(int startYear, int startMonth, int startDom,
            long amount, TemporalUnit unit, int endYear, int endMonth, int endDom) {
        PaxDate end = PaxDate.of(endYear, endMonth, endDom);
        PaxDate expectedStart = PaxDate.of(startYear, startMonth, startDom);
        assertEquals(expectedStart, end.minus(amount, unit));
    }
}
