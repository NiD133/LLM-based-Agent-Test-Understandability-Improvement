package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#addTo(java.time.temporal.Temporal)}.
 */
public class TestMinutes_test_addTo {

    @Test
    public void test_addTo() {
        LocalTime base = LocalTime.of(11, 30);

        // Adding zero minutes leaves the time unchanged.
        assertEquals(LocalTime.of(11, 30), Minutes.of(0).addTo(base));

        // Adding six minutes advances the time from 11:30 to 11:36.
        assertEquals(LocalTime.of(11, 36), Minutes.of(6).addTo(base));
    }
}
