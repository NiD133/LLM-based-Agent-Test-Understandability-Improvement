package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link TaiInstant#durationUntil(TaiInstant)} for a simple positive gap.
 */
public class TestTaiInstant_test_durationUntil_fifteenSeconds {

    @Test
    public void test_durationUntil_fifteenSeconds() {
        // Two TAI instants 15 seconds apart (10s -> 25s), with no nanosecond fraction.
        TaiInstant start = TaiInstant.ofTaiSeconds(10, 0);
        TaiInstant end = TaiInstant.ofTaiSeconds(25, 0);

        Duration gap = start.durationUntil(end);

        // Expect exactly 15 seconds and 0 nanoseconds.
        assertEquals(15, gap.getSeconds());
        assertEquals(0, gap.getNano());
    }
}
