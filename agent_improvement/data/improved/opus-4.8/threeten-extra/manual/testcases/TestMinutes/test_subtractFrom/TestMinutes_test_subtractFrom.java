package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#subtractFrom(java.time.temporal.Temporal)}, which subtracts
 * the held number of minutes from a given temporal object.
 */
public class TestMinutes_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalTime baseTime = LocalTime.of(11, 30);

        // Subtracting zero minutes leaves the time unchanged.
        assertEquals(LocalTime.of(11, 30), Minutes.of(0).subtractFrom(baseTime));

        // Subtracting six minutes from 11:30 gives 11:24.
        assertEquals(LocalTime.of(11, 24), Minutes.of(6).subtractFrom(baseTime));
    }
}
