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
 * Tests {@link BritishCutoverDate#plus(long, TemporalUnit)}.
 *
 * <p>The British calendar cutover dropped the days 1752-09-03 through 1752-09-13:
 * the day after 1752-09-02 is 1752-09-14. The cases below exercise how {@code plus}
 * behaves around that gap as well as during ordinary periods.
 */
public class TestBritishCutoverChronology_test_plus_TemporalUnit {

    /**
     * Cases for {@link #test_plus_TemporalUnit}.
     *
     * <p>Columns: start (year, month, day), amount to add, unit, then the expected
     * result date (year, month, day). The final boolean flags whether the operation
     * is bidirectional (it is not consulted by this test, but is kept so the row
     * layout matches the parameter list).
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // adding DAYS, including across the cutover gap
            { 1752, 9, 2, -1, DAYS, 1752, 9, 1, true },
            { 1752, 9, 2, 0, DAYS, 1752, 9, 2, true },
            { 1752, 9, 2, 1, DAYS, 1752, 9, 14, true },
            { 1752, 9, 2, 2, DAYS, 1752, 9, 15, true },
            { 1752, 9, 14, -1, DAYS, 1752, 9, 2, true },
            { 1752, 9, 14, 0, DAYS, 1752, 9, 14, true },
            { 1752, 9, 14, 1, DAYS, 1752, 9, 15, true },
            { 2014, 5, 26, 0, DAYS, 2014, 5, 26, true },
            { 2014, 5, 26, 8, DAYS, 2014, 6, 3, true },
            { 2014, 5, 26, -3, DAYS, 2014, 5, 23, true },

            // adding WEEKS
            { 1752, 9, 2, -1, WEEKS, 1752, 8, 26, true },
            { 1752, 9, 2, 0, WEEKS, 1752, 9, 2, true },
            { 1752, 9, 2, 1, WEEKS, 1752, 9, 20, true },
            { 1752, 9, 14, -1, WEEKS, 1752, 8, 27, true },
            { 1752, 9, 14, 0, WEEKS, 1752, 9, 14, true },
            { 1752, 9, 14, 1, WEEKS, 1752, 9, 21, true },
            { 2014, 5, 26, 0, WEEKS, 2014, 5, 26, true },
            { 2014, 5, 26, 3, WEEKS, 2014, 6, 16, true },
            { 2014, 5, 26, -5, WEEKS, 2014, 4, 21, true },

            // adding MONTHS, including landing inside the cutover gap (bidi == false)
            { 1752, 9, 2, -1, MONTHS, 1752, 8, 2, true },
            { 1752, 9, 2, 0, MONTHS, 1752, 9, 2, true },
            { 1752, 9, 2, 1, MONTHS, 1752, 10, 2, true },
            { 1752, 9, 14, -1, MONTHS, 1752, 8, 14, true },
            { 1752, 9, 14, 0, MONTHS, 1752, 9, 14, true },
            { 1752, 9, 14, 1, MONTHS, 1752, 10, 14, true },
            { 1752, 8, 12, 1, MONTHS, 1752, 9, 23, false },
            { 1752, 10, 12, -1, MONTHS, 1752, 9, 23, false },
            { 2014, 5, 26, 0, MONTHS, 2014, 5, 26, true },
            { 2014, 5, 26, 3, MONTHS, 2014, 8, 26, true },
            { 2014, 5, 26, -5, MONTHS, 2013, 12, 26, true },

            // adding larger units
            { 2014, 5, 26, 0, YEARS, 2014, 5, 26, true },
            { 2014, 5, 26, 3, YEARS, 2017, 5, 26, true },
            { 2014, 5, 26, -5, YEARS, 2009, 5, 26, true },
            { 2014, 5, 26, 0, DECADES, 2014, 5, 26, true },
            { 2014, 5, 26, 3, DECADES, 2044, 5, 26, true },
            { 2014, 5, 26, -5, DECADES, 1964, 5, 26, true },
            { 2014, 5, 26, 0, CENTURIES, 2014, 5, 26, true },
            { 2014, 5, 26, 3, CENTURIES, 2314, 5, 26, true },
            { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26, true },
            { 2014, 5, 26, 0, MILLENNIA, 2014, 5, 26, true },
            { 2014, 5, 26, 3, MILLENNIA, 5014, 5, 26, true },
            { 2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26, true },
            { 2014, 5, 26, -1, ERAS, -2013, 5, 26, true },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(int year, int month, int dom, long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom, boolean bidi) {
        BritishCutoverDate start = BritishCutoverDate.of(year, month, dom);
        BritishCutoverDate expected = BritishCutoverDate.of(expectedYear, expectedMonth, expectedDom);
        assertEquals(expected, start.plus(amount, unit));
    }
}
