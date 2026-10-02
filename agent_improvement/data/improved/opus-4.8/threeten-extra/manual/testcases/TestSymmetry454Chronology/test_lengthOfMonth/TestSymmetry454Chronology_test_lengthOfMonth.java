package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry454Date#lengthOfMonth()}.
 *
 * <p>In the Symmetry454 calendar a regular year follows a 4-5-4 week pattern, so months
 * have a fixed length depending on their position in the quarter:
 * <ul>
 *   <li>The 1st and 3rd month of each quarter (months 1, 3, 4, 6, 7, 9, 10, 12) have 28 days.</li>
 *   <li>The 2nd month of each quarter (months 2, 5, 8, 11) has 35 days.</li>
 * </ul>
 * In a leap year the extra week is appended to December, giving it 35 days.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_lengthOfMonth {

    /**
     * Cases of {year, month, dayOfMonth, expectedMonthLength}. The day is only used to
     * construct a valid date; the assertion depends solely on year and month.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // Regular year 2000: months alternate 28 / 35 / 28 within each quarter.
            { 2000, 1, 28, 28 },
            { 2000, 2, 28, 35 },
            { 2000, 3, 28, 28 },
            { 2000, 4, 28, 28 },
            { 2000, 5, 28, 35 },
            { 2000, 6, 28, 28 },
            { 2000, 7, 28, 28 },
            { 2000, 8, 28, 35 },
            { 2000, 9, 28, 28 },
            { 2000, 10, 28, 28 },
            { 2000, 11, 28, 35 },
            { 2000, 12, 28, 28 },
            // Leap year 2004: the leap week extends December to 35 days.
            { 2004, 12, 20, 35 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int day, int expectedLength) {
        assertEquals(expectedLength, Symmetry454Date.of(year, month, day).lengthOfMonth());
    }
}
