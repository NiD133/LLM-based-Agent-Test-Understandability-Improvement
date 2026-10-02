package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry010Date#lengthOfMonth()} returns the correct number of days
 * for each month, verified by creating a date at day 1 of each month.
 *
 * <p>In the Symmetry010 calendar:
 * <ul>
 *   <li>Months 1, 3, 4, 6, 7, 9, 10, 12 have 30 days (short months)</li>
 *   <li>Months 2, 5, 8, 11 have 31 days (long months — middle of each quarter)</li>
 *   <li>Month 12 has 37 days in leap years (extra leap week)</li>
 * </ul>
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_lengthOfMonthFirst {

    /**
     * Provides test cases as {year, month, day, expectedLength}.
     * The {@code day} column is present for context but is not used in the test assertion;
     * the test always queries length from day 1 of the month.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // Regular year (2000): months cycle 30/31/30 per quarter
            { 2000, 1,  28, 30 },
            { 2000, 2,  28, 31 },
            { 2000, 3,  28, 30 },
            { 2000, 4,  28, 30 },
            { 2000, 5,  28, 31 },
            { 2000, 6,  28, 30 },
            { 2000, 7,  28, 30 },
            { 2000, 8,  28, 31 },
            { 2000, 9,  28, 30 },
            { 2000, 10, 28, 30 },
            { 2000, 11, 28, 31 },
            { 2000, 12, 28, 30 },
            // Leap year (2004): December gets an extra 7-day leap week → 37 days
            { 2004, 12, 20, 37 },
        };
    }

    /**
     * Verifies that {@code lengthOfMonth()} returns the expected length when called
     * on a date at the first day of each month.
     *
     * <p>The {@code day} parameter from the data source is intentionally ignored here;
     * using day=1 ensures the test is independent of which specific day is chosen.
     */
    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonthFirst(int year, int month, int day, int length) {
        assertEquals(length, Symmetry010Date.of(year, month, 1).lengthOfMonth());
    }
}
