package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link PaxDate#plus(long, TemporalUnit)} crosses the Pax leap-week boundary correctly.
 *
 * <p>In the Pax calendar a leap year inserts a 7-day intercalary 13th month ("Pax"), pushing the
 * normal 13th month to position 14. Adding months or years to a date can therefore land on a
 * shorter month, which clamps the day-of-month. Each case below adds {@code amount} of {@code unit}
 * to the start date and expects exactly the given result date.</p>
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_plus_leap_TemporalUnit {

    /**
     * Cases of the form: {startYear, startMonth, startDom, amount, unit, expectedYear, expectedMonth, expectedDom}.
     */
    public static Object[][] data_plus_leap() {
        return new Object[][] {
            // Adding 1 month from the last day of month 12 (day 26) lands on the 7-day leap month, clamping the day to 7.
            { 2012, 12, 26, 1, MONTHS, 2012, 13, 7 },
            // Subtracting 1 month from month 14 day 26 also lands on the 7-day leap month, clamping the day to 7.
            { 2012, 14, 26, -1, MONTHS, 2012, 13, 7 },
            // Adding 3 years to a leap-month date keeps the same month/day in another leap year.
            { 2012, 13, 6, 3, YEARS, 2015, 13, 6 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leap")
    public void test_plus_leap_TemporalUnit(int year, int month, int dom, long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        PaxDate start = PaxDate.of(year, month, dom);
        PaxDate expected = PaxDate.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.plus(amount, unit));
    }
}
