package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link BritishCutoverDate#lengthOfMonth()}.
 *
 * <p>The British cutover calendar follows the Julian rules up to September 1752 and the
 * Gregorian rules afterwards. The single irregular month is September 1752, when 11 days
 * (the 3rd through the 13th) were dropped, leaving it only 19 days long.
 */
public class TestBritishCutoverChronology_test_lengthOfMonth {

    /**
     * Each case is {year, month, expectedLengthOfMonth}.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // 1700 - a Julian leap year (Julian rule: divisible by 4), so February has 29 days
            { 1700, 1, 31 },
            { 1700, 2, 29 },
            { 1700, 3, 31 },
            { 1700, 4, 30 },
            { 1700, 5, 31 },
            { 1700, 6, 30 },
            { 1700, 7, 31 },
            { 1700, 8, 31 },
            { 1700, 9, 30 },
            { 1700, 10, 31 },
            { 1700, 11, 30 },
            { 1700, 12, 31 },

            // 1751 - an ordinary common year (February has 28 days)
            { 1751, 1, 31 },
            { 1751, 2, 28 },
            { 1751, 3, 31 },
            { 1751, 4, 30 },
            { 1751, 5, 31 },
            { 1751, 6, 30 },
            { 1751, 7, 31 },
            { 1751, 8, 31 },
            { 1751, 9, 30 },
            { 1751, 10, 31 },
            { 1751, 11, 30 },
            { 1751, 12, 31 },

            // 1752 - the cutover year; September lost 11 days, so it has only 19 days
            { 1752, 1, 31 },
            { 1752, 2, 29 },
            { 1752, 3, 31 },
            { 1752, 4, 30 },
            { 1752, 5, 31 },
            { 1752, 6, 30 },
            { 1752, 7, 31 },
            { 1752, 8, 31 },
            { 1752, 9, 19 },
            { 1752, 10, 31 },
            { 1752, 11, 30 },
            { 1752, 12, 31 },

            // 1753 - the first full year after the cutover (February has 28 days)
            { 1753, 1, 31 },
            { 1753, 3, 31 },
            { 1753, 2, 28 },
            { 1753, 4, 30 },
            { 1753, 5, 31 },
            { 1753, 6, 30 },
            { 1753, 7, 31 },
            { 1753, 8, 31 },
            { 1753, 9, 30 },
            { 1753, 10, 31 },
            { 1753, 11, 30 },
            { 1753, 12, 31 },

            // February length across the leap-year boundary:
            // up to 1752 the Julian rule applies (every 4th year is a leap year),
            // afterwards the Gregorian rule applies (century years are leap only if divisible by 400)
            { 1500, 2, 29 },  // Julian leap year
            { 1600, 2, 29 },  // Julian leap year
            { 1700, 2, 29 },  // Julian leap year
            { 1800, 2, 28 },  // Gregorian century, not divisible by 400 -> common year
            { 1900, 2, 28 },  // Gregorian century, not divisible by 400 -> common year
            { 1901, 2, 28 },  // common year
            { 1902, 2, 28 },  // common year
            { 1903, 2, 28 },  // common year
            { 1904, 2, 29 },  // Gregorian leap year
            { 2000, 2, 29 },  // Gregorian century divisible by 400 -> leap year
            { 2100, 2, 28 },  // Gregorian century, not divisible by 400 -> common year
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int expectedLength) {
        BritishCutoverDate firstOfMonth = BritishCutoverDate.of(year, month, 1);
        assertEquals(expectedLength, firstOfMonth.lengthOfMonth());
    }
}
