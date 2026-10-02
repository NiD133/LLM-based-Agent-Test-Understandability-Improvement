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
 * Tests {@link BritishCutoverDate#minus(long, TemporalUnit)}.
 *
 * <p>The test cases are the reversible {@code plus} samples: each row records a
 * base date, an {@code (amount, unit)} step, and the date that results from
 * applying that step. Because {@code minus} is the inverse of {@code plus},
 * subtracting the same step from the result must return the base date.
 */
public class TestBritishCutoverChronology_test_minus_TemporalUnit {

    /**
     * Columns: expected year/month/day, amount, unit, base year/month/day, bidirectional.
     *
     * <p>For this {@code minus} test the "base" date is the operand and the
     * "expected" date is the value produced by subtracting {@code amount} units.
     * Only rows flagged as bidirectional are reversible and therefore exercised.
     */
    public static Object[][] data_plus() {
        return new Object[][] {
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
            { 1752, 9, 2, -1, WEEKS, 1752, 8, 26, true },
            { 1752, 9, 2, 0, WEEKS, 1752, 9, 2, true },
            { 1752, 9, 2, 1, WEEKS, 1752, 9, 20, true },
            { 1752, 9, 14, -1, WEEKS, 1752, 8, 27, true },
            { 1752, 9, 14, 0, WEEKS, 1752, 9, 14, true },
            { 1752, 9, 14, 1, WEEKS, 1752, 9, 21, true },
            { 2014, 5, 26, 0, WEEKS, 2014, 5, 26, true },
            { 2014, 5, 26, 3, WEEKS, 2014, 6, 16, true },
            { 2014, 5, 26, -5, WEEKS, 2014, 4, 21, true },
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
    public void test_minus_TemporalUnit(int expectedYear, int expectedMonth, int expectedDom,
            long amount, TemporalUnit unit, int baseYear, int baseMonth, int baseDom, boolean bidi) {
        if (bidi) {
            BritishCutoverDate base = BritishCutoverDate.of(baseYear, baseMonth, baseDom);
            BritishCutoverDate expected = BritishCutoverDate.of(expectedYear, expectedMonth, expectedDom);
            assertEquals(expected, base.minus(amount, unit));
        }
    }
}
