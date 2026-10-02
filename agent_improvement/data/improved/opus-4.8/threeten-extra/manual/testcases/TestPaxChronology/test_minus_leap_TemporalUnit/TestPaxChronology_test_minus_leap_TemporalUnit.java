package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link PaxDate#minus(long, TemporalUnit)} for cases that cross between
 * a 13-month common year and a 14-month leap year in the Pax calendar.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_minus_leap_TemporalUnit {

    /**
     * Each row describes one subtraction case:
     * <pre>
     * { startYear, startMonth, startDayOfMonth,
     *   amountToSubtract, unit,
     *   expectedYear, expectedMonth, expectedDayOfMonth }
     * </pre>
     */
    public static Object[][] data_minus_leap() {
        return new Object[][] {
            { 2012, 13, 7, -1, MONTHS, 2012, 12, 26 },
            { 2012, 13, 7,  1, MONTHS, 2012, 14, 26 },
            { 2012, 14, 6,  3, YEARS,  2015, 13,  6 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leap")
    public void test_minus_leap_TemporalUnit(int year, int month, int dom, long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        PaxDate start = PaxDate.of(year, month, dom);
        PaxDate expected = PaxDate.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.minus(amount, unit));
    }
}
