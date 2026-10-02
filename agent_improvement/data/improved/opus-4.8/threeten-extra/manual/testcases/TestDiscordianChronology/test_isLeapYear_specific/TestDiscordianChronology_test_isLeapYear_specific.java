package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the Discordian leap-year rule on specific proleptic years.
 * <p>
 * A Discordian year is leap whenever its ISO-aligned offset year
 * ({@code prolepticYear - 1166}) is divisible by 4 (with the usual
 * century exceptions). Around the offset-zero year 1166 this means the
 * leap years fall on 1162, 1166, 1170 and 1174, while every year in
 * between is a common year.
 */
public class TestDiscordianChronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        // Leap years occur every four years: 1162, 1166, 1170, 1174.
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1162), "1162 should be a leap year");
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1166), "1166 should be a leap year");
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1170), "1170 should be a leap year");
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1174), "1174 should be a leap year");

        // Every year between the leap years above is a common (non-leap) year.
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1160), "1160 should be a common year");
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1161), "1161 should be a common year");
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1163), "1163 should be a common year");
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1164), "1164 should be a common year");
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1165), "1165 should be a common year");
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1167), "1167 should be a common year");
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1168), "1168 should be a common year");
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1169), "1169 should be a common year");
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1171), "1171 should be a common year");
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1172), "1172 should be a common year");
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1173), "1173 should be a common year");
    }
}
