package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_plus_leap_TemporalUnit {

    public static Object[][] data_plus_leap() {
        return new Object[][] {
                { 2012, 12, 26, 1, MONTHS, 2012, 13, 7 },
                { 2012, 14, 26, -1, MONTHS, 2012, 13, 7 },
                { 2012, 13, 6, 3, YEARS, 2015, 13, 6 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leap")
    public void test_plus_leap_TemporalUnit(
            int year,
            int month,
            int dom,
            long amount,
            TemporalUnit unit,
            int expectedYear,
            int expectedMonth,
            int expectedDom) {

        test_plus_TemporalUnit(year, month, dom, amount, unit, expectedYear, expectedMonth, expectedDom);
    }

    private void test_plus_TemporalUnit(
            int year,
            int month,
            int dom,
            long amount,
            TemporalUnit unit,
            int expectedYear,
            int expectedMonth,
            int expectedDom) {

        PaxDate actual = PaxDate.of(year, month, dom).plus(amount, unit);

        assertEquals(PaxDate.of(expectedYear, expectedMonth, expectedDom), actual);
    }
}
