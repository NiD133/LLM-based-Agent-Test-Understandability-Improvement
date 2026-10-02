package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_durationUntil_twoNanos {

    @Test
    public void test_durationUntil_twoNanos() {
        // Two instants at the same TAI second, differing by exactly 2 nanoseconds
        TaiInstant tai1 = TaiInstant.ofTaiSeconds(4, 5);
        TaiInstant tai2 = TaiInstant.ofTaiSeconds(4, 7);

        Duration duration = tai1.durationUntil(tai2);

        // Duration spans no whole seconds; only the 2-nanosecond difference remains
        assertEquals(0, duration.getSeconds());
        assertEquals(2, duration.getNano());
    }
}
