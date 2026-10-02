package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link TaiInstant#durationUntil(TaiInstant)} for two instants
 * that are exactly fifteen seconds apart.
 */
public class TestTaiInstant_test_durationUntil_fifteenSeconds {

    @Test
    public void test_durationUntil_fifteenSeconds() {
        TaiInstant start = TaiInstant.ofTaiSeconds(10, 0);
        TaiInstant end = TaiInstant.ofTaiSeconds(25, 0);

        Duration elapsed = start.durationUntil(end);

        assertEquals(15, elapsed.getSeconds(), "expected a 15-second gap");
        assertEquals(0, elapsed.getNano(), "gap should have no nanosecond remainder");
    }
}
