package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link DiscordianDate#of(int, int, int)} rejects field values that fall
 * outside the Discordian calendar's valid ranges.
 * <p>
 * In the Discordian calendar a normal year has 5 months (1..5), each with 73 days (1..73).
 * The only legal use of month 0 / day 0 is St. Tib's Day, which exists solely in leap years;
 * 1900 is not a leap year, so even {@code (1900, 0, 0)} is invalid here.
 */
public class TestDiscordianChronology_test_badDates {

    /**
     * Each row is an {year, month, dayOfMonth} triple that should be rejected.
     * Year 1900 is a non-leap year, so no St. Tib's Day exists.
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // St. Tib's Day (month 0 / day 0) is illegal in the non-leap year 1900
            { 1900, 0, 0 },
            // month out of the 1..5 range
            { 1900, -1, 1 },
            { 1900, 0, 1 },
            { 1900, 6, 1 },
            { 1900, 7, 1 },
            // day-of-month out of the 1..73 range, in the first month
            { 1900, 1, -1 },
            { 1900, 1, 0 },
            { 1900, 1, 74 },
            // duplicate St. Tib's Day case, preserved from the original data set
            { 1900, 0, 0 },
            // day-of-month out of range in the last month
            { 1900, 5, -1 },
            { 1900, 5, 0 },
            { 1900, 5, 74 },
            // day-of-month above 73 in the remaining months
            { 1900, 2, 74 },
            { 1900, 3, 74 },
            { 1900, 4, 74 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(year, month, dom));
    }
}
