package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link TaiInstant#durationUntil(TaiInstant)} when the end instant is
 * earlier than the start instant by a sub-second amount, so the resulting
 * duration is negative.
 */
public class TestTaiInstant_test_durationUntil_twoNanosNegative {

    @Test
    public void durationUntil_returnsNegativeDuration_whenEndInstantIsEarlier() {
        // Start is 2 nanoseconds after end (both within the same TAI second).
        TaiInstant start = TaiInstant.ofTaiSeconds(4, 9);
        TaiInstant end = TaiInstant.ofTaiSeconds(4, 7);

        Duration elapsed = start.durationUntil(end);

        // Going forward from start to end means going backwards in time: -2ns,
        // normalised as -1 second + 999_999_998 nanoseconds.
        assertEquals(-1, elapsed.getSeconds());
        assertEquals(999999998, elapsed.getNano());
    }
}
