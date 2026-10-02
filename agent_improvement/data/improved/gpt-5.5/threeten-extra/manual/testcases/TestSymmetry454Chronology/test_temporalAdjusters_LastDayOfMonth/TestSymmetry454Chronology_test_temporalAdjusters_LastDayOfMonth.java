package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_temporalAdjusters_LastDayOfMonth {

    public static Object[][] data_temporalAdjusters_lastDayOfMonth() {
        return new Object[][] {
                { 2012, 1, 23, 2012, 1, 28 },
                { 2012, 2, 23, 2012, 2, 35 },
                { 2012, 3, 23, 2012, 3, 28 },
                { 2012, 4, 23, 2012, 4, 28 },
                { 2012, 5, 23, 2012, 5, 35 },
                { 2012, 6, 23, 2012, 6, 28 },
                { 2012, 7, 23, 2012, 7, 28 },
                { 2012, 8, 23, 2012, 8, 35 },
                { 2012, 9, 23, 2012, 9, 28 },
                { 2012, 10, 23, 2012, 10, 28 },
                { 2012, 11, 23, 2012, 11, 35 },
                { 2012, 12, 23, 2012, 12, 28 },
                { 2009, 12, 23, 2009, 12, 35 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_temporalAdjusters_lastDayOfMonth")
    public void test_temporalAdjusters_LastDayOfMonth(
            int year,
            int month,
            int day,
            int expectedYear,
            int expectedMonth,
            int expectedDay) {

        Symmetry454Date base = Symmetry454Date.of(year, month, day);
        Symmetry454Date expected = Symmetry454Date.of(expectedYear, expectedMonth, expectedDay);
        Symmetry454Date actual = base.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(expected, actual);
    }
}
