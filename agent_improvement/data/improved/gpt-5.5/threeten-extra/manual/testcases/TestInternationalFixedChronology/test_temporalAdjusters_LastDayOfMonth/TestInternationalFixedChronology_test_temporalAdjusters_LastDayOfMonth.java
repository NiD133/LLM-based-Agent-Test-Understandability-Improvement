package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_temporalAdjusters_LastDayOfMonth {

    public static Object[][] data_temporalAdjusters_lastDayOfMonth() {
        return new Object[][] {
                { 2012, 6, 23, 2012, 6, 29 },
                { 2012, 6, 29, 2012, 6, 29 },
                { 2009, 6, 23, 2009, 6, 28 },
                { 2007, 13, 23, 2007, 13, 29 },
                { 2005, 13, 29, 2005, 13, 29 },
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

        InternationalFixedDate base = InternationalFixedDate.of(year, month, day);
        InternationalFixedDate expected = InternationalFixedDate.of(expectedYear, expectedMonth, expectedDay);

        InternationalFixedDate actual = base.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(expected, actual);
    }
}
