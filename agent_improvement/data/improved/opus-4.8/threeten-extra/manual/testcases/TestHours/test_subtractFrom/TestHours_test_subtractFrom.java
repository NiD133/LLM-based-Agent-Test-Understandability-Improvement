package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#subtractFrom(java.time.temporal.Temporal)}, which subtracts
 * the held number of hours from a given temporal.
 */
public class TestHours_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalTime baseTime = LocalTime.of(11, 30);

        // Subtracting zero hours leaves the time unchanged.
        assertEquals(LocalTime.of(11, 30), Hours.of(0).subtractFrom(baseTime));

        // Subtracting six hours moves 11:30 back to 05:30.
        assertEquals(LocalTime.of(5, 30), Hours.of(6).subtractFrom(baseTime));
    }
}
