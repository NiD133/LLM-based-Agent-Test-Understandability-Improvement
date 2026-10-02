package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry454Date#plus(long, TemporalUnit)} for dates around the
 * end of a leap year (year 2015, which has the extra "leap week" appended to
 * December, giving that month 35 days).
 * <p>
 * The point of these cases is to confirm that adding days, weeks, months and
 * years correctly rolls across the leap week and into the following year.
 */
public class TestSymmetry454Chronology_test_plus_leapWeek_TemporalUnit {

    /**
     * Each case is: a starting Symmetry454 date (year, month, day-of-month), an
     * amount and unit to add, and the expected resulting date (year, month,
     * day-of-month). The start date 2015-12-28 is the last "normal" day before
     * the leap week in December 2015.
     */
    static Arguments[] data_plus_leapWeek() {
        return new Arguments[] {
            // Adding days, including crossing the leap week into the next year.
            Arguments.of(2015, 12, 28,  0, DAYS,   2015, 12, 28),
            Arguments.of(2015, 12, 28,  8, DAYS,   2016,  1,  1),
            Arguments.of(2015, 12, 28, -3, DAYS,   2015, 12, 25),

            // Adding weeks; note the 52-week step lands in the next year.
            Arguments.of(2015, 12, 28,  0, WEEKS,  2015, 12, 28),
            Arguments.of(2015, 12, 28,  3, WEEKS,  2016,  1, 14),
            Arguments.of(2015, 12, 28, -5, WEEKS,  2015, 11, 28),
            Arguments.of(2015, 12, 28, 52, WEEKS,  2016, 12, 21),

            // Adding months.
            Arguments.of(2015, 12, 28,  0, MONTHS, 2015, 12, 28),
            Arguments.of(2015, 12, 28,  3, MONTHS, 2016,  3, 28),
            Arguments.of(2015, 12, 28, -5, MONTHS, 2015,  7, 28),
            Arguments.of(2015, 12, 28, 12, MONTHS, 2016, 12, 28),

            // Adding years.
            Arguments.of(2015, 12, 28,  0, YEARS,  2015, 12, 28),
            Arguments.of(2015, 12, 28,  3, YEARS,  2018, 12, 28),
            Arguments.of(2015, 12, 28, -5, YEARS,  2010, 12, 28),
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leapWeek")
    public void test_plus_leapWeek_TemporalUnit(int year, int month, int dom, long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        Symmetry454Date start = Symmetry454Date.of(year, month, dom);
        Symmetry454Date expected = Symmetry454Date.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.plus(amount, unit));
    }
}
