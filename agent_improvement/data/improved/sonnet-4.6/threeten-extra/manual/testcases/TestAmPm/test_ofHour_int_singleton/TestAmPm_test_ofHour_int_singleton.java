package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AmPm#ofHour(int)} returns the shared enum singleton
 * (AM or PM) for every valid hour-of-day value (0–23).
 *
 * Because AmPm is an enum, every call for the same half-day must return
 * the identical object reference — not just an equal one.
 */
public class TestAmPm_test_ofHour_int_singleton {

    /** Hours 0–11 belong to AM (ante meridiem). */
    @Test
    public void test_ofHour_amHours_returnAmSingleton() {
        for (int i = 0; i < 12; i++) {
            assertSame(AmPm.AM, AmPm.ofHour(i));
        }
    }

    /** Hours 12–23 belong to PM (post meridiem). */
    @Test
    public void test_ofHour_pmHours_returnPmSingleton() {
        for (int i = 12; i < 24; i++) {
            assertSame(AmPm.PM, AmPm.ofHour(i));
        }
    }
}
