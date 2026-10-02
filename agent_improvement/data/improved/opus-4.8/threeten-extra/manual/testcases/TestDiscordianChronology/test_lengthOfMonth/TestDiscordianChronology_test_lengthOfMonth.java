package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link DiscordianDate#lengthOfMonth()}.
 * <p>
 * Every regular Discordian month has 73 days, regardless of the year or the
 * month number, so {@code lengthOfMonth()} is expected to always return 73.
 */
public class TestDiscordianChronology_test_lengthOfMonth {

    private static final int DAYS_IN_DISCORDIAN_MONTH = 73;

    /**
     * Provides {year, month, expectedLength} cases. The cases cover every month
     * within a single year and the first month across several different years
     * (including leap years) to confirm the length never varies.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // All five months of a single year.
            { 1900, 1, DAYS_IN_DISCORDIAN_MONTH },
            { 1900, 2, DAYS_IN_DISCORDIAN_MONTH },
            { 1900, 3, DAYS_IN_DISCORDIAN_MONTH },
            { 1900, 4, DAYS_IN_DISCORDIAN_MONTH },
            { 1900, 5, DAYS_IN_DISCORDIAN_MONTH },
            // First month across several years, including leap years.
            { 1901, 1, DAYS_IN_DISCORDIAN_MONTH },
            { 1902, 1, DAYS_IN_DISCORDIAN_MONTH },
            { 1903, 1, DAYS_IN_DISCORDIAN_MONTH },
            { 1904, 1, DAYS_IN_DISCORDIAN_MONTH },
            { 1966, 1, DAYS_IN_DISCORDIAN_MONTH },
            { 2066, 1, DAYS_IN_DISCORDIAN_MONTH },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int expectedLength) {
        DiscordianDate firstOfMonth = DiscordianDate.of(year, month, 1);

        assertEquals(expectedLength, firstOfMonth.lengthOfMonth());
    }
}
