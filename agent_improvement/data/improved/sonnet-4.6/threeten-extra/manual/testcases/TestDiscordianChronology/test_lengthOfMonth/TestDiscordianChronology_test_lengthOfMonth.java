package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_lengthOfMonth {

    // Each Discordian month always has exactly 73 days (St. Tib's Day is month 0, not tested here).
    // Data: year, month, expectedLength
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // All five months in a non-leap year have 73 days
            { 1900, 1, 73 },
            { 1900, 2, 73 },
            { 1900, 3, 73 },
            { 1900, 4, 73 },
            { 1900, 5, 73 },
            // Month 1 across consecutive years (including a leap year, 1904)
            { 1901, 1, 73 },
            { 1902, 1, 73 },
            { 1903, 1, 73 },
            { 1904, 1, 73 },
            // Sampling other years: century year (1966) and future year (2066)
            { 1966, 1, 73 },
            { 2066, 1, 73 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, DiscordianDate.of(year, month, 1).lengthOfMonth());
    }
}
