package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.temporal.TemporalAdjusters;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_temporalAdjusters_LastDayOfMonth {

    /**
     * Test data for lastDayOfMonth adjuster in the Symmetry010 calendar.
     *
     * In the Symmetry010 calendar each quarter follows a 30-31-30 day pattern:
     *   months 1,3,4,6,7,9,10,12 have 30 days (normal months)
     *   months 2,5,8,11          have 31 days (long months)
     * Leap years append an extra week (7 days) to December, giving it 37 days.
     *
     * Columns: year, month, day, expectedYear, expectedMonth, expectedDay
     */
    public static Object[][] data_temporalAdjusters_lastDayOfMonth() {
        return new Object[][] {
            // Normal months (30 days)
            { 2012,  1, 23,  2012,  1, 30 },
            { 2012,  3, 23,  2012,  3, 30 },
            { 2012,  4, 23,  2012,  4, 30 },
            { 2012,  6, 23,  2012,  6, 30 },
            { 2012,  7, 23,  2012,  7, 30 },
            { 2012,  9, 23,  2012,  9, 30 },
            { 2012, 10, 23,  2012, 10, 30 },
            { 2012, 12, 23,  2012, 12, 30 },
            // Long months (31 days): February, May, August, November
            { 2012,  2, 23,  2012,  2, 31 },
            { 2012,  5, 23,  2012,  5, 31 },
            { 2012,  8, 23,  2012,  8, 31 },
            { 2012, 11, 23,  2012, 11, 31 },
            // Leap year: December has 37 days (extra leap week appended)
            { 2009, 12, 23,  2009, 12, 37 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_temporalAdjusters_lastDayOfMonth")
    public void test_temporalAdjusters_LastDayOfMonth(int year, int month, int day, int expectedYear, int expectedMonth, int expectedDay) {
        Symmetry010Date base = Symmetry010Date.of(year, month, day);
        Symmetry010Date expected = Symmetry010Date.of(expectedYear, expectedMonth, expectedDay);
        Symmetry010Date actual = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(expected, actual);
    }
}
