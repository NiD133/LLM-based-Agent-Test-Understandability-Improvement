package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link TemporalAdjusters#lastDayOfMonth()} resolves to the correct
 * last day of the month for {@link InternationalFixedDate}.
 *
 * <p>In the International Fixed calendar most months have 28 days, but month 6
 * gains a 29th day (Leap Day) in leap years and month 13 always ends on a 29th
 * day (Year Day). The adjuster must respect those longer months.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_temporalAdjusters_LastDayOfMonth {

    /**
     * Cases as {startYear, startMonth, startDay, expectedYear, expectedMonth, expectedDay},
     * where the expected date is the last day of the start date's month.
     */
    public static Object[][] data_temporalAdjusters_lastDayOfMonth() {
        return new Object[][] {
            // Leap year: month 6 ends on the 29th (Leap Day)
            { 2012, 6, 23, 2012, 6, 29 },
            // Already on the last day: stays put
            { 2012, 6, 29, 2012, 6, 29 },
            // Non-leap year: month 6 ends on the regular 28th
            { 2009, 6, 23, 2009, 6, 28 },
            // Month 13 always ends on the 29th (Year Day)
            { 2007, 13, 23, 2007, 13, 29 },
            { 2005, 13, 29, 2005, 13, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_temporalAdjusters_lastDayOfMonth")
    public void test_temporalAdjusters_LastDayOfMonth(int year, int month, int day,
            int expectedYear, int expectedMonth, int expectedDay) {
        InternationalFixedDate base = InternationalFixedDate.of(year, month, day);
        InternationalFixedDate expectedLastDay = InternationalFixedDate.of(expectedYear, expectedMonth, expectedDay);

        InternationalFixedDate actual = base.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(expectedLastDay, actual);
    }
}
