package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry454Date#lengthOfMonth()}.
 *
 * <p>In the Symmetry454 calendar every quarter follows a 4-5-4 week pattern, so the
 * second month of each quarter (February, May, August, November) has 35 days while all
 * other months have 28 days. In a leap year an extra week is appended to December,
 * making December 35 days long.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_lengthOfMonthFirst {

    /**
     * Each case is {year, month, expectedLengthOfMonth}.
     * The length is queried from the first day of the month.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // A common year: the 4-5-4 pattern gives 28 / 35 / 28 days per quarter.
            { 2000, 1, 28 },
            { 2000, 2, 35 },
            { 2000, 3, 28 },
            { 2000, 4, 28 },
            { 2000, 5, 35 },
            { 2000, 6, 28 },
            { 2000, 7, 28 },
            { 2000, 8, 35 },
            { 2000, 9, 28 },
            { 2000, 10, 28 },
            { 2000, 11, 35 },
            { 2000, 12, 28 },
            // A leap year: the extra leap week extends December to 35 days.
            { 2004, 12, 35 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonthFirst(int year, int month, int expectedLength) {
        Symmetry454Date firstOfMonth = Symmetry454Date.of(year, month, 1);

        assertEquals(expectedLength, firstOfMonth.lengthOfMonth());
    }
}
