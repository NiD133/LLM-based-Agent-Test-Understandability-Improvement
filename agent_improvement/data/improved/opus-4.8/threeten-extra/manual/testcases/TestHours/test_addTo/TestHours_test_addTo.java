package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#addTo(java.time.temporal.Temporal)}.
 */
public class TestHours_test_addTo {

    @Test
    public void test_addTo() {
        LocalTime startTime = LocalTime.of(11, 30);

        // Adding zero hours leaves the time unchanged.
        assertEquals(LocalTime.of(11, 30), Hours.of(0).addTo(startTime));

        // Adding six hours advances the time by six hours.
        assertEquals(LocalTime.of(17, 30), Hours.of(6).addTo(startTime));
    }
}
