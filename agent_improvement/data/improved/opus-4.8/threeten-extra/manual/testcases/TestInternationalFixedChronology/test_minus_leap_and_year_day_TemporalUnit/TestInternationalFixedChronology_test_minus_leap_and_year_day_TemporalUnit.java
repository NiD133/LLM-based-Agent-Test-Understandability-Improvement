package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@code InternationalFixedDate.minus(amount, unit)} for dates that involve the
 * calendar's two special "out of week" days:
 * <ul>
 *   <li>Leap Day  - day 29 of month 6, present only in leap years.</li>
 *   <li>Year Day  - day 29 of month 13, present every year.</li>
 * </ul>
 * Each case subtracts an amount in some unit from a start date and checks the resulting date.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_minus_leap_and_year_day_TemporalUnit {

    /**
     * Cases for {@link #test_minus_leap_and_year_day_TemporalUnit}.
     * <p>
     * Each row reads as: {@code of(startYear, startMonth, startDom).minus(amount, unit)}
     * is expected to equal {@code of(expectedYear, expectedMonth, expectedDom)}.
     * Columns: startYear, startMonth, startDom, amount, unit, expectedYear, expectedMonth, expectedDom.
     */
    public static Object[][] data_minus_leap_and_year_day() {
        return new Object[][] {
            // --- subtracting from Year Day (2014/13/29) ---
            { 2014, 13, 29,      0, DAYS,   2014, 13, 29 },
            { 2014, 13, 29,      8, DAYS,   2014, 13, 21 },
            { 2014, 13, 29,     -3, DAYS,   2015,  1,  3 },
            { 2014, 13, 29,      0, WEEKS,  2014, 13, 29 },
            { 2014, 13, 29,      3, WEEKS,  2014, 13,  7 },
            { 2014, 13, 29,     -5, WEEKS,  2015,  2,  7 },
            { 2014, 13, 29,     52, WEEKS,  2013, 13, 29 },
            { 2014, 13, 29,      0, MONTHS, 2014, 13, 29 },
            { 2014, 13, 29,      3, MONTHS, 2014, 10, 28 },
            { 2014, 13, 29,     -5, MONTHS, 2015,  5, 28 },
            { 2014, 13, 29,     13, MONTHS, 2013, 13, 29 },
            { 2014, 13, 29,      0, YEARS,  2014, 13, 29 },
            { 2014, 13, 29,      3, YEARS,  2011, 13, 29 },
            { 2014, 13, 29,     -5, YEARS,  2019, 13, 29 },

            // --- subtracting whole weeks that cross between Year Day and Leap Day ---
            { 2011, 13, 29,  4 * -6, WEEKS, 2012,  6, 29 },
            { 2012, 13, 29,   4 * 7, WEEKS, 2012,  6, 29 },

            // --- subtracting from Leap Day (2012/6/29) ---
            { 2012,  6, 29,      0, DAYS,   2012,  6, 29 },
            { 2012,  6, 29,      8, DAYS,   2012,  6, 21 },
            { 2012,  6, 29,     -3, DAYS,   2012,  7,  3 },
            { 2012,  6, 29,      0, WEEKS,  2012,  6, 29 },
            { 2012,  6, 29,      3, WEEKS,  2012,  6,  8 },
            { 2012,  6, 29,     -5, WEEKS,  2012,  8,  8 },
            { 2012, 13, 29,     28, WEEKS,  2012,  6, 29 },
            { 2012,  6, 29,  52 * 4, WEEKS, 2008,  6, 29 },
            { 2012,  6, 29,      0, MONTHS, 2012,  6, 29 },
            { 2012,  6, 29,      3, MONTHS, 2012,  3, 28 },
            { 2012,  6, 29,     -5, MONTHS, 2012, 11, 28 },
            { 2012,  6, 29,  13 * 4, MONTHS, 2008, 6, 29 },
            { 2012,  6, 29,      0, YEARS,  2012,  6, 29 },
            { 2012,  6, 29,      3, YEARS,  2009,  6, 28 },
            { 2012,  6, 29,     -5, YEARS,  2017,  6, 28 },
            { 2012,  6, 29,      4, YEARS,  2008,  6, 29 },
            { 2012,  6, 29,  4 * -7, WEEKS, 2012, 13, 29 },
            { 2012,  6, 29,   4 * 6, WEEKS, 2011, 13, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leap_and_year_day")
    public void test_minus_leap_and_year_day_TemporalUnit(
            int startYear, int startMonth, int startDom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {

        InternationalFixedDate start = InternationalFixedDate.of(startYear, startMonth, startDom);
        InternationalFixedDate expected = InternationalFixedDate.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.minus(amount, unit));
    }
}
