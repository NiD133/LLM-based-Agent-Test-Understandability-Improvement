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

/**
 * Tests {@link PaxDate#plus(long, TemporalUnit)}: adding (or subtracting, via a
 * negative amount) a number of temporal units to a Pax-calendar date.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_plus_TemporalUnit {

    /**
     * Cases for {@link #test_plus_TemporalUnit}.
     * <p>
     * Each row is: {@code startYear, startMonth, startDay, amount, unit,
     * expectedYear, expectedMonth, expectedDay}. The expectation is that
     * {@code PaxDate(start).plus(amount, unit)} equals {@code PaxDate(expected)}.
     * <p>
     * The Pax calendar has 13 ordinary months of 28 days, plus a short 7-day
     * leap month ("Pax", month 13) that is inserted before the final month in
     * leap years, giving those years 14 months. The cases below cover each unit
     * with a zero, a positive, and a negative amount, then exercise the
     * leap-month rollover behaviour.
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // DAYS
            { 2014, 5, 26, 0, DAYS, 2014, 5, 26 },
            { 2014, 5, 26, 8, DAYS, 2014, 6, 6 },
            { 2014, 5, 26, -3, DAYS, 2014, 5, 23 },
            // WEEKS
            { 2014, 5, 26, 0, WEEKS, 2014, 5, 26 },
            { 2014, 5, 26, 3, WEEKS, 2014, 6, 19 },
            { 2014, 5, 26, -5, WEEKS, 2014, 4, 19 },
            // MONTHS
            { 2014, 5, 26, 0, MONTHS, 2014, 5, 26 },
            { 2014, 5, 26, 3, MONTHS, 2014, 8, 26 },
            { 2014, 5, 26, -5, MONTHS, 2013, 13, 26 },
            // YEARS
            { 2014, 5, 26, 0, YEARS, 2014, 5, 26 },
            { 2014, 5, 26, 3, YEARS, 2017, 5, 26 },
            { 2014, 5, 26, -5, YEARS, 2009, 5, 26 },
            // DECADES
            { 2014, 5, 26, 0, DECADES, 2014, 5, 26 },
            { 2014, 5, 26, 3, DECADES, 2044, 5, 26 },
            { 2014, 5, 26, -5, DECADES, 1964, 5, 26 },
            // CENTURIES
            { 2014, 5, 26, 0, CENTURIES, 2014, 5, 26 },
            { 2014, 5, 26, 3, CENTURIES, 2314, 5, 26 },
            { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 },
            // MILLENNIA
            { 2014, 5, 26, 0, MILLENNIA, 2014, 5, 26 },
            { 2014, 5, 26, 3, MILLENNIA, 5014, 5, 26 },
            { 2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26 },
            // ERAS (stepping back one era flips the proleptic year)
            { 2014, 5, 26, -1, ERAS, -2013, 5, 26 },
            // Leap-month rollover within and across leap years
            { 2012, 13, 6, 3, MONTHS, 2013, 2, 6 },
            { 2011, 13, 26, 1, YEARS, 2012, 14, 26 },
            { 2014, 13, 26, -2, YEARS, 2012, 14, 26 },
            { 2012, 14, 26, -6, YEARS, 2006, 14, 26 },
            { 2012, 13, 6, -6, YEARS, 2006, 13, 6 },
            // Negative proleptic years
            { -2014, 5, 26, 0, MONTHS, -2014, 5, 26 },
            { -2014, 5, 26, 3, MONTHS, -2014, 8, 26 },
            { -2014, 5, 26, -5, MONTHS, -2015, 13, 26 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(int year, int month, int dom, long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        PaxDate start = PaxDate.of(year, month, dom);
        PaxDate expected = PaxDate.of(expectedYear, expectedMonth, expectedDom);
        assertEquals(expected, start.plus(amount, unit));
    }
}
