package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#addTo(java.time.temporal.Temporal)}.
 */
public class TestSeconds_test_addTo {

    @Test
    public void addTo_shiftsTemporalForwardByTheNumberOfSeconds() {
        LocalTime baseTime = LocalTime.of(11, 30);

        // Adding zero seconds leaves the temporal unchanged.
        assertEquals(LocalTime.of(11, 30), Seconds.of(0).addTo(baseTime));

        // Adding six seconds advances the time by six seconds.
        assertEquals(LocalTime.of(11, 30, 6), Seconds.of(6).addTo(baseTime));
    }
}
