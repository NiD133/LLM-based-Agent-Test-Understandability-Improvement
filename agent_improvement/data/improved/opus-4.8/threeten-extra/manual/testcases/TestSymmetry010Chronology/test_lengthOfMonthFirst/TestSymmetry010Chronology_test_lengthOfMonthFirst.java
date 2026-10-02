package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link Symmetry010Date#lengthOfMonth()} for the first day of each month.
 * <p>
 * In the Symmetry010 calendar the months of a quarter alternate 30 / 31 / 30 days
 * (so February, May, August and November have 31 days), and December gains an extra
 * leap week in a leap year, giving it 37 days.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_lengthOfMonthFirst {

    /**
     * Each case is {year, month, expectedMonthLength}.
     * 2000 is a common year (December has 30 days); 2004 is a leap year (December has 37 days).
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            { 2000, 1, 30 },   // January   - short
            { 2000, 2, 31 },   // February  - long (middle of quarter 1)
            { 2000, 3, 30 },   // March     - short
            { 2000, 4, 30 },   // April     - short
            { 2000, 5, 31 },   // May       - long (middle of quarter 2)
            { 2000, 6, 30 },   // June      - short
            { 2000, 7, 30 },   // July      - short
            { 2000, 8, 31 },   // August    - long (middle of quarter 3)
            { 2000, 9, 30 },   // September - short
            { 2000, 10, 30 },  // October   - short
            { 2000, 11, 31 },  // November  - long (middle of quarter 4)
            { 2000, 12, 30 },  // December  - short (common year)
            { 2004, 12, 37 },  // December  - long (leap year, includes leap week)
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonthFirst(int year, int month, int expectedLength) {
        Symmetry010Date firstOfMonth = Symmetry010Date.of(year, month, 1);
        assertEquals(expectedLength, firstOfMonth.lengthOfMonth());
    }
}
