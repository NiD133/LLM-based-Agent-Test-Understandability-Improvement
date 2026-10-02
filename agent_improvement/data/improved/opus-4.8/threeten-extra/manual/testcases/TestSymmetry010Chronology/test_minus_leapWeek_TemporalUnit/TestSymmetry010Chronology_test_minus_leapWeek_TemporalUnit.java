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
 * Verifies {@link Symmetry010Date#minus(long, TemporalUnit)} on dates that fall in or
 * near a leap week (year 2015 is a Symmetry010 leap year, with a 37-day December).
 *
 * <p>Each test case describes a single subtraction:
 * {@code of(startDate).minus(amount, unit)} must equal {@code expectedDate}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_minus_leapWeek_TemporalUnit {

    /**
     * Test cases laid out as:
     * { startYear, startMonth, startDay, amount, unit, expectedYear, expectedMonth, expectedDay }.
     */
    public static Object[][] data_minus_leapWeek() {
        return new Object[][] {
            // subtracting days
            { 2015, 12, 28,  0, DAYS,   2015, 12, 28 },
            { 2015, 12, 36,  8, DAYS,   2015, 12, 28 },
            { 2015, 12, 25, -3, DAYS,   2015, 12, 28 },
            // subtracting weeks
            { 2015, 12, 28,  0, WEEKS,  2015, 12, 28 },
            { 2016,  1, 12,  3, WEEKS,  2015, 12, 28 },
            { 2015, 11, 24, -5, WEEKS,  2015, 12, 28 },
            { 2016, 12, 21, 52, WEEKS,  2015, 12, 28 },
            // subtracting months
            { 2015, 12, 28,  0, MONTHS, 2015, 12, 28 },
            { 2016,  3, 28,  3, MONTHS, 2015, 12, 28 },
            { 2015,  7, 28, -5, MONTHS, 2015, 12, 28 },
            { 2016, 12, 28, 12, MONTHS, 2015, 12, 28 },
            // subtracting years
            { 2015, 12, 28,  0, YEARS,  2015, 12, 28 },
            { 2018, 12, 28,  3, YEARS,  2015, 12, 28 },
            { 2010, 12, 28, -5, YEARS,  2015, 12, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leapWeek")
    public void test_minus_leapWeek_TemporalUnit(int startYear, int startMonth, int startDay,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDay) {
        Symmetry010Date start = Symmetry010Date.of(startYear, startMonth, startDay);
        Symmetry010Date expected = Symmetry010Date.of(expectedYear, expectedMonth, expectedDay);

        assertEquals(expected, start.minus(amount, unit));
    }
}
