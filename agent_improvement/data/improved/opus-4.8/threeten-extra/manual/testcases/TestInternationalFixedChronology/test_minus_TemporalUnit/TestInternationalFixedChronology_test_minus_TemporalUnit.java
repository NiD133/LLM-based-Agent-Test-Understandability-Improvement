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
 * Tests {@link InternationalFixedDate#minus(long, TemporalUnit)}.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_minus_TemporalUnit {

    /**
     * Cases for subtracting an amount of a temporal unit from an International Fixed date.
     * <p>
     * Each row is laid out in the order the assertion reads it:
     * <pre>{ startYear, startMonth, startDom, amountToSubtract, unit, expectedYear, expectedMonth, expectedDom }</pre>
     * meaning {@code IFC(start).minus(amountToSubtract, unit) == IFC(expected)}.
     */
    public static Object[][] data_minus() {
        return new Object[][] {
            { 2014, 5, 26, 0, DAYS, 2014, 5, 26 },
            { 2014, 6, 6, 8, DAYS, 2014, 5, 26 },
            { 2014, 5, 23, -3, DAYS, 2014, 5, 26 },
            { 2014, 5, 26, 0, WEEKS, 2014, 5, 26 },
            { 2014, 6, 19, 3, WEEKS, 2014, 5, 26 },
            { 2014, 4, 19, -5, WEEKS, 2014, 5, 26 },
            { 2014, 5, 26, 0, MONTHS, 2014, 5, 26 },
            { 2014, 8, 26, 3, MONTHS, 2014, 5, 26 },
            { 2013, 13, 26, -5, MONTHS, 2014, 5, 26 },
            { 2014, 5, 26, 0, YEARS, 2014, 5, 26 },
            { 2017, 5, 26, 3, YEARS, 2014, 5, 26 },
            { 2009, 5, 26, -5, YEARS, 2014, 5, 26 },
            { 2014, 5, 26, 0, DECADES, 2014, 5, 26 },
            { 2044, 5, 26, 3, DECADES, 2014, 5, 26 },
            { 1964, 5, 26, -5, DECADES, 2014, 5, 26 },
            { 2014, 5, 26, 0, CENTURIES, 2014, 5, 26 },
            { 2314, 5, 26, 3, CENTURIES, 2014, 5, 26 },
            { 1514, 5, 26, -5, CENTURIES, 2014, 5, 26 },
            { 2014, 5, 26, 0, MILLENNIA, 2014, 5, 26 },
            { 5014, 5, 26, 3, MILLENNIA, 2014, 5, 26 },
            { 2014 - 1000, 5, 26, -1, MILLENNIA, 2014, 5, 26 },
            // Subtracting weeks across month and year-end boundaries.
            { 2015, 1, 19, 3, WEEKS, 2014, 13, 26 },
            { 2013, 13, 19, -5, WEEKS, 2014, 1, 26 },
            { 2012, 7, 19, 3, WEEKS, 2012, 6, 26 },
            { 2012, 6, 19, -5, WEEKS, 2012, 7, 26 },
            { 2013, 6, 28, 52 + 1, WEEKS, 2012, 6, 21 },
            { 2019, 6, 28, 6 * 52 + 1, WEEKS, 2013, 6, 21 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_TemporalUnit(int year, int month, int dom, long amountToSubtract, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        InternationalFixedDate start = InternationalFixedDate.of(year, month, dom);
        InternationalFixedDate expected = InternationalFixedDate.of(expectedYear, expectedMonth, expectedDom);
        assertEquals(expected, start.minus(amountToSubtract, unit));
    }
}
