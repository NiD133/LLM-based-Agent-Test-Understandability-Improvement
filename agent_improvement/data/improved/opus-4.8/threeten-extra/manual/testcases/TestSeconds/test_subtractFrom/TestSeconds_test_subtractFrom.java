package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#subtractFrom(java.time.temporal.Temporal)}.
 * <p>
 * Subtracting a {@code Seconds} amount from a temporal should move it
 * backwards in time by that number of seconds, leaving a zero amount unchanged.
 */
public class TestSeconds_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalTime base = LocalTime.of(11, 30);

        // Subtracting zero seconds leaves the temporal unchanged.
        assertEquals(LocalTime.of(11, 30), Seconds.of(0).subtractFrom(base));

        // Subtracting 6 seconds moves 11:30:00 back to 11:29:54.
        assertEquals(LocalTime.of(11, 29, 54), Seconds.of(6).subtractFrom(base));
    }
}
