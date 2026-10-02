package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianChronology#isLeapYear(long)} correctly identifies
 * leap years. Discordian leap years align with Gregorian (ISO) leap years via
 * the fixed offset: ISO_YEAR = DISCORDIAN_YEAR - 1166.
 * A year is a Discordian leap year if its corresponding ISO year is divisible by 4,
 * except centuries not divisible by 400.
 */
public class TestDiscordianChronology_test_isLeapYear_specific {

    private static final DiscordianChronology CHRONO = DiscordianChronology.INSTANCE;

    @Test
    public void test_isLeapYear_specific() {
        // Discordian 1174 = ISO year 8: divisible by 4, leap year
        assertTrue(CHRONO.isLeapYear(1174));

        // Discordian 1173, 1172, 1171 = ISO 7, 6, 5: not divisible by 4, not leap years
        assertFalse(CHRONO.isLeapYear(1173));
        assertFalse(CHRONO.isLeapYear(1172));
        assertFalse(CHRONO.isLeapYear(1171));

        // Discordian 1170 = ISO year 4: divisible by 4, leap year
        assertTrue(CHRONO.isLeapYear(1170));

        // Discordian 1169, 1168, 1167 = ISO 3, 2, 1: not divisible by 4, not leap years
        assertFalse(CHRONO.isLeapYear(1169));
        assertFalse(CHRONO.isLeapYear(1168));
        assertFalse(CHRONO.isLeapYear(1167));

        // Discordian 1166 = ISO year 0: divisible by 4, leap year
        assertTrue(CHRONO.isLeapYear(1166));

        // Discordian 1165, 1164, 1163 = ISO -1, -2, -3: not divisible by 4, not leap years
        assertFalse(CHRONO.isLeapYear(1165));
        assertFalse(CHRONO.isLeapYear(1164));
        assertFalse(CHRONO.isLeapYear(1163));

        // Discordian 1162 = ISO year -4: divisible by 4, leap year
        assertTrue(CHRONO.isLeapYear(1162));

        // Discordian 1161, 1160 = ISO -5, -6: not divisible by 4, not leap years
        assertFalse(CHRONO.isLeapYear(1161));
        assertFalse(CHRONO.isLeapYear(1160));
    }
}
