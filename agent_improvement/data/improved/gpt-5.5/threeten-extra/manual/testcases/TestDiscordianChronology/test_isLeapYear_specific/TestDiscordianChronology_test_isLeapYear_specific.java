package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        // Leap years follow the ISO year that is offset by 1166 years.
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1174));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1173));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1172));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1171));

        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1170));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1169));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1168));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1167));

        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1166));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1165));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1164));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1163));

        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1162));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1161));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1160));
    }
}
