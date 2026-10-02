package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link TaiInstant#durationUntil(TaiInstant)} when the two instants
 * share the same second and differ only in their nanosecond fraction.
 */
public class TestTaiInstant_test_durationUntil_twoNanos {

    @Test
    public void durationUntil_sameSecond_returnsNanoDifference() {
        TaiInstant start = TaiInstant.ofTaiSeconds(4, 5);
        TaiInstant end = TaiInstant.ofTaiSeconds(4, 7);

        Duration elapsed = start.durationUntil(end);

        assertEquals(0, elapsed.getSeconds(), "no whole seconds between the instants");
        assertEquals(2, elapsed.getNano(), "nanosecond difference is 7 - 5");
    }
}
