package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link PaxDate#lengthOfMonth()}.
 *
 * <p>In the Pax calendar every month is 28 days long, with one exception: during
 * a leap year a short month named "Pax" (only 7 days long) is inserted as month 13,
 * pushing the final 28-day month to position 14. Therefore:
 * <ul>
 *   <li>Non-leap year: 13 months, all 28 days long.</li>
 *   <li>Leap year: 14 months, all 28 days long except the 7-day "Pax" month at position 13.</li>
 * </ul>
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_lengthOfMonth {

    private static final int STANDARD_MONTH_LENGTH = 28;
    private static final int PAX_MONTH_LENGTH = 7;

    /**
     * Each case is {year, month, expectedLengthOfMonth}.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // 1900 is a leap year (last two digits 00, not divisible by 400):
            // 14 months, with the 7-day "Pax" month at position 13.
            { 1900, 1, STANDARD_MONTH_LENGTH },
            { 1900, 2, STANDARD_MONTH_LENGTH },
            { 1900, 3, STANDARD_MONTH_LENGTH },
            { 1900, 4, STANDARD_MONTH_LENGTH },
            { 1900, 5, STANDARD_MONTH_LENGTH },
            { 1900, 6, STANDARD_MONTH_LENGTH },
            { 1900, 7, STANDARD_MONTH_LENGTH },
            { 1900, 8, STANDARD_MONTH_LENGTH },
            { 1900, 9, STANDARD_MONTH_LENGTH },
            { 1900, 10, STANDARD_MONTH_LENGTH },
            { 1900, 11, STANDARD_MONTH_LENGTH },
            { 1900, 12, STANDARD_MONTH_LENGTH },
            { 1900, 13, PAX_MONTH_LENGTH },
            { 1900, 14, STANDARD_MONTH_LENGTH },

            // Non-leap years: 13 months, the last one being a full 28 days.
            { 1901, 13, STANDARD_MONTH_LENGTH },
            { 1902, 13, STANDARD_MONTH_LENGTH },
            { 1903, 13, STANDARD_MONTH_LENGTH },
            { 1904, 13, STANDARD_MONTH_LENGTH },
            { 1905, 13, STANDARD_MONTH_LENGTH },

            // 1906 is a leap year (last two digits 06, divisible by 6).
            { 1906, 13, PAX_MONTH_LENGTH },

            // 2000 is divisible by 400, so it is NOT a leap year.
            { 2000, 13, STANDARD_MONTH_LENGTH },

            // 2100 is a leap year (last two digits 00, not divisible by 400).
            { 2100, 13, PAX_MONTH_LENGTH },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int expectedLength) {
        PaxDate firstDayOfMonth = PaxDate.of(year, month, 1);
        assertEquals(expectedLength, firstDayOfMonth.lengthOfMonth());
    }
}
