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
public class TestPaxChronology_test_plus_TemporalUnit {

    /**
     * Test data for {@link #test_plus_TemporalUnit}.
     *
     * <p>Each row: startYear, startMonth, startDay, amount, unit,
     *              expectedYear, expectedMonth, expectedDay
     *
     * <p>Cases cover every supported {@link java.time.temporal.ChronoUnit} (DAYS through ERAS),
     * positive/negative/zero amounts, month-boundary crossings, leap-year interactions,
     * and BCE dates (negative proleptic years).
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // --- DAYS ---
            { 2014, 5, 26,  0, DAYS, 2014, 5, 26 },   // zero — no change
            { 2014, 5, 26,  8, DAYS, 2014, 6,  6 },   // positive — crosses month boundary
            { 2014, 5, 26, -3, DAYS, 2014, 5, 23 },   // negative — within same month

            // --- WEEKS ---
            { 2014, 5, 26,  0, WEEKS, 2014, 5, 26 },  // zero — no change
            { 2014, 5, 26,  3, WEEKS, 2014, 6, 19 },  // positive — crosses month boundary
            { 2014, 5, 26, -5, WEEKS, 2014, 4, 19 },  // negative — crosses month boundary

            // --- MONTHS ---
            { 2014, 5, 26,  0, MONTHS, 2014,  5, 26 },  // zero — no change
            { 2014, 5, 26,  3, MONTHS, 2014,  8, 26 },  // positive — within same year
            { 2014, 5, 26, -5, MONTHS, 2013, 13, 26 },  // negative — crosses year boundary into leap month

            // --- YEARS ---
            { 2014, 5, 26,  0, YEARS, 2014, 5, 26 },   // zero — no change
            { 2014, 5, 26,  3, YEARS, 2017, 5, 26 },   // positive
            { 2014, 5, 26, -5, YEARS, 2009, 5, 26 },   // negative

            // --- DECADES ---
            { 2014, 5, 26,  0, DECADES, 2014, 5, 26 },  // zero — no change
            { 2014, 5, 26,  3, DECADES, 2044, 5, 26 },  // positive
            { 2014, 5, 26, -5, DECADES, 1964, 5, 26 },  // negative

            // --- CENTURIES ---
            { 2014, 5, 26,  0, CENTURIES, 2014, 5, 26 },  // zero — no change
            { 2014, 5, 26,  3, CENTURIES, 2314, 5, 26 },  // positive
            { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 },  // negative

            // --- MILLENNIA ---
            { 2014, 5, 26,  0, MILLENNIA,       2014, 5, 26 },  // zero — no change
            { 2014, 5, 26,  3, MILLENNIA,       5014, 5, 26 },  // positive
            { 2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26 },  // negative

            // --- ERAS ---
            { 2014, 5, 26, -1, ERAS, -2013, 5, 26 },  // subtracting one era flips CE → BCE

            // --- leap-year and inter-month edge cases ---
            // Adding 3 months to a leap-month (13) date crosses into the following year
            { 2012, 13,  6,  3, MONTHS, 2013,  2,  6 },
            // Adding 1 year to a non-leap 13th month pushes the date into the 14th month of the leap target
            { 2011, 13, 26,  1, YEARS,  2012, 14, 26 },
            // Subtracting 2 years from a non-leap 13th month also lands in a leap year's 14th month
            { 2014, 13, 26, -2, YEARS,  2012, 14, 26 },
            // Staying in a leap year's 14th month across a multi-year subtraction
            { 2012, 14, 26, -6, YEARS,  2006, 14, 26 },
            // Staying in a leap year's 13th month across a multi-year subtraction
            { 2012, 13,  6, -6, YEARS,  2006, 13,  6 },

            // --- BCE (negative proleptic year) ---
            { -2014, 5, 26,  0, MONTHS, -2014,  5, 26 },  // zero — no change
            { -2014, 5, 26,  3, MONTHS, -2014,  8, 26 },  // positive — within same BCE year
            { -2014, 5, 26, -5, MONTHS, -2015, 13, 26 },  // negative — crosses BCE year boundary
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
            PaxDate.of(expectedYear, expectedMonth, expectedDom),
            PaxDate.of(year, month, dom).plus(amount, unit));
    }
}
