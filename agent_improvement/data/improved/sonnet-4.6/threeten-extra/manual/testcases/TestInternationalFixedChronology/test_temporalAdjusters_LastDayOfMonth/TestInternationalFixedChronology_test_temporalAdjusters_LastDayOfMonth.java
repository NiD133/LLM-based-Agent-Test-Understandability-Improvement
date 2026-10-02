package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link TemporalAdjusters#lastDayOfMonth()} returns the correct last day
 * for {@link InternationalFixedDate} across regular months, month 6 (which gains a
 * Leap Day in leap years), and month 13 (which always has Year Day as its 29th day).
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_temporalAdjusters_LastDayOfMonth {

    /**
     * Provides (year, month, day, expectedYear, expectedMonth, expectedDay) tuples.
     *
     * In the International Fixed calendar:
     *  - Regular months have 28 days.
     *  - Month 6 has 29 days in a leap year (day 29 = Leap Day).
     *  - Month 13 always has 29 days (day 29 = Year Day).
     */
    public static Object[][] data_temporalAdjusters_lastDayOfMonth() {
        return new Object[][] {
            // Leap year (2012): month 6 ends on day 29 (Leap Day)
            { 2012, 6, 23, 2012, 6, 29 },
            { 2012, 6, 29, 2012, 6, 29 },

            // Non-leap year (2009): month 6 ends on day 28
            { 2009, 6, 23, 2009, 6, 28 },

            // Month 13 (Sol) always ends on day 29 (Year Day)
            { 2007, 13, 23, 2007, 13, 29 },
            { 2005, 13, 29, 2005, 13, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_temporalAdjusters_lastDayOfMonth")
    public void test_temporalAdjusters_LastDayOfMonth(
            int year, int month, int day,
            int expectedYear, int expectedMonth, int expectedDay) {
        InternationalFixedDate base = InternationalFixedDate.of(year, month, day);
        InternationalFixedDate expected = InternationalFixedDate.of(expectedYear, expectedMonth, expectedDay);
        InternationalFixedDate actual = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(expected, actual);
    }
}
