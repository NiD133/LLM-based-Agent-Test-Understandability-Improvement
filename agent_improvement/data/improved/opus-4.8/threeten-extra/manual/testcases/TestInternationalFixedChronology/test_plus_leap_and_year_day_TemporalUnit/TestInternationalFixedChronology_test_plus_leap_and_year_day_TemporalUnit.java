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
 * Tests that adding an amount of a {@link TemporalUnit} to an
 * {@link InternationalFixedDate} produces the expected date, with special
 * focus on the calendar's two intercalary days:
 * <ul>
 *   <li>Leap Day  - day 29 of month 6, present only in leap years.</li>
 *   <li>Year Day  - day 29 of month 13, present every year.</li>
 * </ul>
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_plus_leap_and_year_day_TemporalUnit {

    /**
     * Cases of the form: start date (year, month, dom), the amount and unit to
     * add, then the expected resulting date (year, month, dom).
     */
    public static Object[][] data_plus_leap_and_year_day() {
        return new Object[][] {
            // Starting from Year Day (month 13, day 29)
            { 2014, 13, 29,      0, DAYS,   2014, 13, 29 },
            { 2014, 13, 29,      8, DAYS,   2015,  1,  8 },
            { 2014, 13, 29,     -3, DAYS,   2014, 13, 26 },
            { 2014, 13, 29,      0, WEEKS,  2014, 13, 29 },
            { 2014, 13, 29,      3, WEEKS,  2015,  1, 21 },
            { 2014, 13, 29,     -5, WEEKS,  2014, 12, 21 },
            { 2014, 13, 29,     52, WEEKS,  2015, 13, 29 },
            { 2014, 13, 29,      0, MONTHS, 2014, 13, 29 },
            { 2014, 13, 29,      3, MONTHS, 2015,  3, 28 },
            { 2014, 13, 29,     -5, MONTHS, 2014,  8, 28 },
            { 2014, 13, 29,     13, MONTHS, 2015, 13, 29 },
            { 2014, 13, 29,      0, YEARS,  2014, 13, 29 },
            { 2014, 13, 29,      3, YEARS,  2017, 13, 29 },
            { 2014, 13, 29,     -5, YEARS,  2009, 13, 29 },
            { 2011, 13, 29,  4 * 6, WEEKS,  2012,  6, 29 },
            { 2012, 13, 29, 4 * -7, WEEKS,  2012,  6, 29 },

            // Starting from Leap Day (month 6, day 29, leap year only)
            { 2012,  6, 29,      0, DAYS,   2012,  6, 29 },
            { 2012,  6, 29,      8, DAYS,   2012,  7,  8 },
            { 2012,  6, 29,     -3, DAYS,   2012,  6, 26 },
            { 2012,  6, 29,      0, WEEKS,  2012,  6, 29 },
            { 2012,  6, 29,      3, WEEKS,  2012,  7, 22 },
            { 2012,  6, 29,     -5, WEEKS,  2012,  5, 22 },
            { 2012,  6, 29, 52 * 4, WEEKS,  2016,  6, 29 },
            { 2012,  6, 29,      0, MONTHS, 2012,  6, 29 },
            { 2012,  6, 29,      3, MONTHS, 2012,  9, 28 },
            { 2012,  6, 29,     -5, MONTHS, 2012,  1, 28 },
            { 2012,  6, 29, 13 * 4, MONTHS, 2016,  6, 29 },
            { 2012,  6, 29,      0, YEARS,  2012,  6, 29 },
            { 2012,  6, 29,      3, YEARS,  2015,  6, 28 },
            { 2012,  6, 29,     -5, YEARS,  2007,  6, 28 },
            { 2012,  6, 29,      4, YEARS,  2016,  6, 29 },
            { 2012,  6, 29,  4 * 7, WEEKS,  2012, 13, 29 },
            { 2012,  6, 29, 4 * -6, WEEKS,  2011, 13, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leap_and_year_day")
    public void test_plus_leap_and_year_day_TemporalUnit(int year, int month, int dom,
            long amount, TemporalUnit unit, int expectedYear, int expectedMonth, int expectedDom) {
        InternationalFixedDate start = InternationalFixedDate.of(year, month, dom);
        InternationalFixedDate expected = InternationalFixedDate.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.plus(amount, unit));
    }
}
