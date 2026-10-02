package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link Symmetry454Date#minus(long, TemporalUnit)} for dates near the
 * leap week at the end of year 2015 (a leap year in the Symmetry454 calendar,
 * whose December has 35 days).
 *
 * <p>Every case starts from some date, subtracts {@code amount} of the given
 * {@code unit}, and expects to land back on 2015-12-28.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_minus_leapWeek_TemporalUnit {

    /**
     * Test cases as {@code { startYear, startMonth, startDay, amount, unit,
     * expectedYear, expectedMonth, expectedDay }}, where
     * {@code start.minus(amount, unit) == expected}.
     */
    public static Object[][] data_minus_leapWeek() {
        return new Object[][] {
            // subtract days
            { 2015, 12, 28,  0, DAYS,   2015, 12, 28 },
            { 2016,  1,  1,  8, DAYS,   2015, 12, 28 },
            { 2015, 12, 25, -3, DAYS,   2015, 12, 28 },
            // subtract weeks (crossing the leap-week boundary)
            { 2015, 12, 28,  0, WEEKS,  2015, 12, 28 },
            { 2016,  1, 14,  3, WEEKS,  2015, 12, 28 },
            { 2015, 11, 28, -5, WEEKS,  2015, 12, 28 },
            { 2016, 12, 21, 52, WEEKS,  2015, 12, 28 },
            // subtract months
            { 2015, 12, 28,  0, MONTHS, 2015, 12, 28 },
            { 2016,  3, 28,  3, MONTHS, 2015, 12, 28 },
            { 2015,  7, 28, -5, MONTHS, 2015, 12, 28 },
            { 2016, 12, 28, 12, MONTHS, 2015, 12, 28 },
            // subtract years
            { 2015, 12, 28,  0, YEARS,  2015, 12, 28 },
            { 2018, 12, 28,  3, YEARS,  2015, 12, 28 },
            { 2010, 12, 28, -5, YEARS,  2015, 12, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leapWeek")
    public void test_minus_leapWeek_TemporalUnit(
            int startYear, int startMonth, int startDay,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDay) {
        Symmetry454Date start = Symmetry454Date.of(startYear, startMonth, startDay);
        Symmetry454Date expected = Symmetry454Date.of(expectedYear, expectedMonth, expectedDay);

        assertEquals(expected, start.minus(amount, unit));
    }
}
