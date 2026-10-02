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
 * Verifies {@link Symmetry010Date#plus(long, TemporalUnit)} for dates that fall
 * in a leap year, i.e. a year whose December holds an extra "leap week" (days 31-37).
 *
 * <p>Every case starts from a date in the leap December of 2015 and adds an amount
 * of some unit, asserting the resulting date matches the expected year/month/day.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_plus_leapWeek_TemporalUnit {

    /**
     * Cases for {@link #test_plus_leapWeek_TemporalUnit}.
     *
     * <p>Each row is: start (year, month, day), the amount and unit to add,
     * then the expected result (year, month, day).
     */
    public static Object[][] data_plus_leapWeek() {
        return new Object[][] {
            // Adding days, including crossing into the leap week (day 36)
            { 2015, 12, 28,  0, DAYS,   2015, 12, 28 },
            { 2015, 12, 28,  8, DAYS,   2015, 12, 36 },
            { 2015, 12, 28, -3, DAYS,   2015, 12, 25 },

            // Adding weeks, including a full 52-week year roll-over
            { 2015, 12, 28,  0, WEEKS,  2015, 12, 28 },
            { 2015, 12, 28,  3, WEEKS,  2016,  1, 12 },
            { 2015, 12, 28, -5, WEEKS,  2015, 11, 24 },
            { 2015, 12, 28, 52, WEEKS,  2016, 12, 21 },

            // Adding months, including a full 12-month year roll-over
            { 2015, 12, 28,  0, MONTHS, 2015, 12, 28 },
            { 2015, 12, 28,  3, MONTHS, 2016,  3, 28 },
            { 2015, 12, 28, -5, MONTHS, 2015,  7, 28 },
            { 2015, 12, 28, 12, MONTHS, 2016, 12, 28 },

            // Adding years
            { 2015, 12, 28,  0, YEARS,  2015, 12, 28 },
            { 2015, 12, 28,  3, YEARS,  2018, 12, 28 },
            { 2015, 12, 28, -5, YEARS,  2010, 12, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leapWeek")
    public void test_plus_leapWeek_TemporalUnit(int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        Symmetry010Date start = Symmetry010Date.of(year, month, dom);
        Symmetry010Date expected = Symmetry010Date.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.plus(amount, unit));
    }
}
